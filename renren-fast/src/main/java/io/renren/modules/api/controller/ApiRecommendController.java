package io.renren.modules.api.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.gson.Gson;

import io.renren.common.utils.AppValidateUtils;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.alipay.AlipayUtil;
import io.renren.common.utils.alipay.PayUtil;
import io.renren.common.utils.wxpay.FileUtil;
import io.renren.common.utils.wxpay.WxPayUtil;
import io.renren.common.utils.wxpay.WxUtil;
import io.renren.common.utils.wxpay.XmlUtil;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.ProductTypeDTO;
import io.renren.modules.api.entity.dto.ShoppingCartDTO;
import io.renren.modules.api.entity.dto.TownDTO;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.yh.entity.AccountEntity;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.entity.OrderintegrationEntity;
import io.renren.modules.yh.entity.enums.EnumOrderType;
import io.renren.modules.yh.service.AccountService;
import io.renren.modules.yh.service.CollectionService;
import io.renren.modules.yh.service.ConfigtableService;
import io.renren.modules.yh.service.OrderService;
import io.renren.modules.yh.service.OrderintegrationService;
import io.renren.modules.yh.service.ProductService;
import io.renren.modules.yh.service.ProducttypeService;
import io.renren.modules.yh.service.RegionService;

@RequestMapping("/api")
@RestController
public class ApiRecommendController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ApiRecommendController.class);
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProducttypeService producttypeService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private OrderintegrationService orderintegrationService;
	
	@Autowired
	private ConfigtableService configtableService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private AccountService accountService;
	
	@AuthIgnore
	@RequestMapping("town")
	public R town(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			List<TownDTO> region = regionService.apiTown(map.get("areaID"));			
			return R.ok().put("info", region);
		}else{
			return R.ok();
		}
		
		
	}
	
	@AuthIgnore
	@RequestMapping("submitOrder")
	public R submitOrder(HttpServletRequest request){
		
		String userId = request.getParameter("userID");
		String orderAddress = request.getParameter("orderAddress");
		String townId = request.getParameter("townID");
		String orderDetailAddress = request.getParameter("orderDetailAddress");
		String orderSendTime = request.getParameter("orderSendTime");
		String receiverPhone = request.getParameter("receiverPhone");
		String receiverName = request.getParameter("receiverName");
		String mark = request.getParameter("mark");
		String useIntegralCount = request.getParameter("useIntegralCount");
		String orderPayType = request.getParameter("payType");
		String orderAllPrice = request.getParameter("orderAllPrice");
		String orderProductionsID = request.getParameter("orderProductionsID");
		String orderProductionsCount = request.getParameter("orderProductionsCount");	
		String actualPayPrice=request.getParameter("actualPayPrice");
		String orderID = request.getParameter("orderID");
		
		OrderEntity orderEntity = new OrderEntity();		
		
		orderEntity.setOrderId(orderID);
		orderEntity.setOrderCreateTime(new Date());				
		orderEntity.setOrderType(EnumOrderType.TOBEPAID.getStatus());//默认待支付
		orderEntity.setUserId(Long.valueOf(userId));
		orderEntity.setOrderAddress(orderAddress);
		orderEntity.setTownId(Integer.valueOf(townId));
		orderEntity.setOrderDetailAddress(orderDetailAddress);
		orderEntity.setOrderSendTime(DateUtils.parse(orderSendTime, DateUtils.DATE_PATTERN));
		orderEntity.setReceiverPhone(receiverPhone);
		orderEntity.setReceiverName(receiverName);
		orderEntity.setMark(mark);
		orderEntity.setUserIntegralCount(Long.valueOf(useIntegralCount));
		orderEntity.setOrderPayType(Integer.valueOf(orderPayType));
		orderEntity.setOrderAllPrice(new BigDecimal(orderAllPrice));
		orderEntity.setActualPayPrice(new BigDecimal(actualPayPrice));
		
		
		Map<String, Object> info = new HashMap<String, Object>();
		
		R r = orderService.apiSubmitOrder(orderEntity,orderProductionsID,orderProductionsCount);
		
		return r;
	}
	
	@AuthIgnore
    @PostMapping("aliNotify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        // 获取到返回的所有参数 先判断是否交易成功trade_status 再做签名校验
        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        // 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
        // 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
        // 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
        // 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
		LOG.info("----------------接收阿里Notify");
		System.out.println("----------------接收阿里Notify");
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayUtil.ALIPAY_PUBLIC_KEY, "UTF-8", "RSA");
            if (flag) {

                String useIntegral = URLDecoder.decode(params.get("passback_params"), "UTF-8");
                String orderNo = params.get("out_trade_no");
                
                LOG.info("----------------本次订单号（orderNo）:"+orderNo);
                System.out.println("----------------本次订单号（orderNo）:"+orderNo);
                
                OrderEntity orderEntity = orderService.queryObject(orderNo);
                if(orderEntity == null) {
                	LOG.info("----------------不是本系统的订单！订单号:"+orderNo);
                	System.out.println("----------------不是本系统的订单！订单号:"+orderNo);
                	
                    PrintWriter writer = response.getWriter();
                    writer.write("success");//success通知支付宝不用再重复通知，但业务不处理此异常回调
                    writer.close();
                    return;
                }
                
                //如果是我们的订单，则获取销售员id
                SysUserEntity userEntity = sysUserService.queryObject(orderEntity.getUserId());
                
                BigDecimal amount = new BigDecimal(params.get("total_amount"));
                if(amount.compareTo(orderEntity.getActualPayPrice()) != 0) {
                    PrintWriter writer = response.getWriter();
                    writer.write("success");
                    writer.close();
                    return;
                }
                
                LOG.info("----------------seller_id:"+params.get("seller_id"));
                
                if(!AlipayUtil.SELLER_ID.equals(params.get("seller_id"))) {
                    PrintWriter writer = response.getWriter();
                    writer.write("success");
                    writer.close();
                    return;
                }
                if(!AlipayUtil.ALIPAY_APPID.equals(params.get("app_id"))) {
                    PrintWriter writer = response.getWriter();
                    writer.write("success");
                    writer.close();
                    return;
                }
                
                LOG.info("----------------订单支付验签成功:"+new Gson().toJson(params));
            	System.out.println("----------------订单支付验签成功:"+new Gson().toJson(params));
            	
                PrintWriter writer = response.getWriter();
                writer.write("success");
                writer.close();
                
                //支付
    			orderEntity.setOrderType(EnumOrderType.PAID.getStatus());//支付完成：已支付
    			orderService.update(orderEntity);
    			
    			LOG.info("----------------订单支付成功:"+new Gson().toJson(params));
    			System.out.println("----------------订单支付成功:"+new Gson().toJson(params));
                
                //支付成功 处理业务
                //记得写销售员积分
				ConfigtableEntity configtableEntity = configtableService.getConfig(userEntity);
				if(configtableEntity!=null){
					
					//得到比例，算的积分
					String proportion = configtableEntity.getConfigValue();
					BigDecimal allPrice = orderEntity.getOrderAllPrice();
					Long thisIntegral = allPrice.longValue()*Long.valueOf(proportion); 	
					
					//写入积分明显表
					OrderintegrationEntity orderintegration = new OrderintegrationEntity();
					orderintegration.setUserId(userEntity.getUserId());
					orderintegration.setOrderId(orderEntity.getOrderId());
					orderintegration.setOrderSumPrice(allPrice);
					orderintegration.setIntegration(thisIntegral);
					orderintegration.setPriceIntegrationType(2);//2销售积分 1配送积分		
				    orderintegration.setTime(new Date());
					//注意：这里设计的是给配送人员用的，是否兑换过
					//orderintegration.setIsRebate(1);//0未兑换过，1兑换过
				    try{
				    	orderintegrationService.save(orderintegration);
				    	LOG.info("订单号："+orderEntity.getOrderId()+"销售员："+userEntity.getUserId()+"本次订单积分："+thisIntegral);
				    	System.out.println("订单号："+orderEntity.getOrderId()+"销售员："+userEntity.getUserId()+"本次订单积分："+thisIntegral);
				    	
				    }catch (Exception e) {
						LOG.error("---------------积分明细写入错误！"+e.getMessage(),e);
						System.out.println("---------------积分明细写入错误！"+e.getMessage());
					}
				    
					
				    Long luseIntegral = StringUtils.isNotBlank(useIntegral)?Long.valueOf(useIntegral):0;
				    
					//计算积分    减去之前 加上 现在得到的
				    Long addIntegral = thisIntegral + userEntity.getUserIntegral()-luseIntegral;					
					
				    try{
				    	sysUserService.addIntegral(addIntegral,userEntity.getUserId());
				    	LOG.info("订单号："+orderEntity.getOrderId()+"销售员："+userEntity.getUserId()+"本次订单积分："+thisIntegral
				    			+"用户使用积分："+useIntegral+"用户原积分："+userEntity.getUserIntegral()+"计算之后积分："+addIntegral);
				    	System.out.println("订单号："+orderEntity.getOrderId()+"销售员："+userEntity.getUserId()+"本次订单积分："+thisIntegral
				    			+"用户使用积分："+useIntegral+"用户原积分："+userEntity.getUserIntegral()+"计算之后积分："+addIntegral);
				    	
				    }catch (Exception e) {
				    	LOG.error("---------------积分累加写入！"+e.getMessage(),e);
				    	System.out.println("---------------积分累加写入！"+e.getMessage());
					}
					
				}
				
				//支付成功 像区域管理员加钱
				AccountEntity account = new AccountEntity();
				account.setEnterpriseId(userEntity.getBelongToAgencyId());
				account.setPrice(orderEntity.getOrderAllPrice());
				try{
					accountService.updateByAgency(account);
					LOG.info("区域经销商id："+userEntity.getBelongToAgencyId()+"本次账户所加金额："+orderEntity.getOrderAllPrice());
					System.out.println("区域经销商id："+userEntity.getBelongToAgencyId()+"本次账户所加金额："+orderEntity.getOrderAllPrice());
				}catch (Exception e) {
					LOG.error("---------------区域经销商账号加钱错误!"+e.getMessage(),e);
					System.out.println("---------------区域经销商账号加钱错误!"+e.getMessage());
				}
				
                
         
            } else {
                LOG.info("--------------------订单支付验签失败：" +new Gson().toJson(params));
                System.out.println("--------------------订单支付验签失败：" +new Gson().toJson(params));
                // TODO 验签失败则记录异常日志，并在response中返回failure.
                PrintWriter writer = response.getWriter();
                writer.write("failure");
                writer.close();
            }

        }
        LOG.info("----------------trade_status:"+request.getParameter("trade_status"));
        System.out.println("----------------trade_status:"+request.getParameter("trade_status"));
    }
	
	@AuthIgnore
	@RequestMapping("wxNotify")
	public void orderPayNotify(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("[/api/wxNotify]");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		try {
			ServletInputStream in = request.getInputStream();
			String resxml = FileUtil.readInputStream2String(in);
			Map<String, Object> restmap = XmlUtil.xmlParse(resxml);
			LOG.info("支付结果通知：" + restmap);
			if ("SUCCESS".equals(restmap.get("return_code"))) {
				// 订单支付成功 业务处理
				String out_trade_no = String.valueOf(restmap.get("out_trade_no")); // 商户订单号
				String total_fee =String.valueOf(restmap.get("total_fee"));
				String useIntegral = restmap.get("attach").toString();
				// 通过商户订单判断是否该订单已经处理 如果处理跳过 如果未处理先校验sign签名 再进行订单业务相关的处理
				
			    OrderEntity orderEntity = orderService.queryObject(out_trade_no);
                if(orderEntity == null) {
                    PrintWriter writer = response.getWriter();
                    writer.write(setXML("SUCCESS", ""));
                    writer.close();
                    return;
                }else{
                	if(orderEntity.getOrderAllPrice().compareTo(new BigDecimal(total_fee))!=0){
                		PrintWriter writer = response.getWriter();
                        writer.write(setXML("SUCCESS", ""));
                        writer.close();
                        return;
                	}
                }
                
                //如果是我们的订单，则获取销售员id
                SysUserEntity userEntity = sysUserService.queryObject(orderEntity.getUserId());

				String sing = restmap.get("sign").toString(); // 返回的签名
				restmap.remove("sign");
				String signnow = WxPayUtil.getSign(restmap, WxUtil.API_SECRET);
				if (signnow.equals(sing)) {
					// 进行业务处理
					LOG.info("订单支付通知： 支付成功，订单号" + out_trade_no);
					
					  //支付
	    			orderEntity.setOrderType(EnumOrderType.PAID.getStatus());//支付完成：已支付
	    			orderService.update(orderEntity);
	                
	                //支付成功 处理业务
	                //记得写销售员积分
					ConfigtableEntity configtableEntity = configtableService.getConfig(userEntity);
					if(configtableEntity!=null){
						
						//得到比例，算的积分
						String proportion = configtableEntity.getConfigValue();
						BigDecimal allPrice = orderEntity.getOrderAllPrice();
						Long thisIntegral = allPrice.longValue()*Long.valueOf(proportion); 	
						
						//写入积分明显表
						OrderintegrationEntity orderintegration = new OrderintegrationEntity();
						orderintegration.setUserId(userEntity.getUserId());
						orderintegration.setOrderId(orderEntity.getOrderId());
						orderintegration.setOrderSumPrice(allPrice);
						orderintegration.setIntegration(thisIntegral);
						orderintegration.setPriceIntegrationType(2);//2销售积分 1配送积分		
					    orderintegration.setTime(new Date());
						//注意：这里设计的是给配送人员用的，是否兑换过
						//orderintegration.setIsRebate(1);//0未兑换过，1兑换过
					    orderintegrationService.save(orderintegration);
						
					    Long luseIntegral = StringUtils.isNotBlank(useIntegral)?Long.valueOf(useIntegral):0;
					    
						//计算积分    减去之前 加上 现在得到的
					    Long addIntegral = thisIntegral + userEntity.getUserIntegral()-luseIntegral;					
						
						sysUserService.addIntegral(addIntegral,userEntity.getUserId());
						
						//支付成功 像区域管理员加钱
						AccountEntity account = new AccountEntity();
						account.setEnterpriseId(userEntity.getBelongToAgencyId());
						account.setPrice(orderEntity.getOrderAllPrice());
						accountService.updateByAgency(account);
				} else {
					LOG.info("订单支付通知：签名错误");
				}
			} else {
				LOG.info("订单支付通知：支付失败，" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	
	@AuthIgnore
	@RequestMapping("hotSaleProduction")
	public R hotSaleProduction(HttpServletRequest request,@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
		String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			List<CollectionDTO> info = productService.apiHotSaleProduction(map.get("areaID"),map.get("userID"));
			return R.ok().put("info", info);
		}else{
			return R.error();
		}		
		
	}
	
	@AuthIgnore
	@RequestMapping("searchProduction")
	public R searchProduction(@RequestParam Map<String, String> map){		
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			List<CollectionDTO> info = productService.apiSearchProduction(map.get("keyword"),map.get("areaID"),map.get("userID"));
			
			return R.ok().put("info", info);
		}else{
			return R.error();
		}
		

	}
	
	@AuthIgnore
	@RequestMapping("shoppingCartList")
	public R shoppingCartList(HttpServletRequest request,@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
		/*List<ShoppingCartDTO> info = productService.apiShoppingCartList(map.get("userID"),map.get("areaID"));
		return R.ok().put("info", info);*/
        String websign = AppValidateUtils.getSign(map);
		if(websign.equals(sign)){
			String priceLimit = configtableService.apiPriceLimit(map.get("userID"),map.get("areaID"));
			List<ShoppingCartDTO> info = productService.apiShoppingCartList(map.get("userID"),map.get("areaID"));
			
			Map<String,Object> returnMap = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(priceLimit)){
				returnMap.put("priceLimit", priceLimit);
			}else{
				returnMap.put("priceLimit", 0);
			}
			
			returnMap.put("info", info);
			
		    return R.ok(returnMap);
		}else{
			return R.error();
		}	
	}
	
	@AuthIgnore
	@RequestMapping("getCategory")
	public R getCategory(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			List<ProductTypeDTO> info = producttypeService.apiGetCategory(map.get("userID"),map.get("areaID"));
			return R.ok().put("info", info);
		}else{
			return R.error();
		}	
	}
	
	@AuthIgnore
	@RequestMapping("collectProduction")
	public R collectProduction(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			collectionService.apiCollectProduction(map.get("userID"),map.get("productId"),map.get("isCollected"),new Date());
			return R.ok();
		}else{
			return R.error();
		}
	}
	
	
    private static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }
	
	
	

}
