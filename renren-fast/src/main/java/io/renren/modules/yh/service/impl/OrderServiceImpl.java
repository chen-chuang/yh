package io.renren.modules.yh.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayConstants;
import com.google.gson.Gson;

import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.alipay.AlipayUtil;
import io.renren.common.utils.alipay.DatetimeUtil;
import io.renren.common.utils.alipay.PayUtil;
import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.api.entity.dto.OrderProductions;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.dao.ConfigtableDao;
import io.renren.modules.yh.dao.OrderDao;
import io.renren.modules.yh.dao.OrderdetailDao;
import io.renren.modules.yh.dao.OrderintegrationDao;
import io.renren.modules.yh.dao.ProductDao;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.entity.OrderintegrationEntity;
import io.renren.modules.yh.entity.enums.EnumOrderType;
import io.renren.modules.yh.service.OrderService;
import net.sf.json.JSON;



@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderdetailDao orderdetailDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ConfigtableDao configtableDao;
	
	@Autowired
	private SysUserDao SysUserDao;
	
	@Autowired
	private OrderintegrationDao orderintegrationDao;
	
	
	
	@Override
	public OrderEntity queryObject(String orderId){
		return orderDao.queryObject(orderId);
	}
	
	@Override
	public List<OrderEntity> queryList(Map<String, Object> map){
		return orderDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return orderDao.queryTotal(map);
	}
	
	@Override
	public void save(OrderEntity order){
		orderDao.save(order);
	}
	
	@Override
	public void update(OrderEntity order){
		orderDao.update(order);
	}
	
	@Override
	public void delete(String orderId){
		orderDao.delete(orderId);
	}
	
	@Override
	public void deleteBatch(String[] orderIds){
		orderDao.deleteBatch(orderIds);
	}
	
	@Override
	public List<OrderDetailInfo> apiOrderList(String userID, String orderType){
		
		List<OrderDetailInfo> orderDetailInfo = orderDao.apiOrderList(userID, orderType);
		
		for(OrderDetailInfo order : orderDetailInfo){
			List<OrderProductions> orderProductions = orderdetailDao.apiOrderDetailList(order.getOrderInfo().getOrderID());
			
			order.getOrderInfo().setOrderProductions(orderProductions);
		}	
		
		return orderDetailInfo;
	}
	
	@Override
	public List<OrderDetailInfo> apiQueryOrder(String startTime, String endTime, String townID){
		
		Date dStartTime = null;
		
		Date dEndTime = null;
		
		
		if(StringUtils.isNotBlank(startTime)){
			dStartTime =DateUtils.parse(startTime, DateUtils.DATE_TIME_PATTERN);
		}
		
		if(StringUtils.isNotBlank(startTime)){
			dEndTime =DateUtils.parse(endTime, DateUtils.DATE_TIME_PATTERN);
		}
		
		List<OrderDetailInfo> orderDetailInfo = orderDao.apiQueryOrder(dStartTime, dEndTime, townID);
		
		for(OrderDetailInfo order : orderDetailInfo){
			List<OrderProductions> orderProductions = orderdetailDao.apiOrderDetailList(order.getOrderInfo().getOrderID());
			
			order.getOrderInfo().setOrderProductions(orderProductions);
		}	
		
		return orderDetailInfo;
	}

	@Override
	@Transactional	
	public R apiSubmitOrder(OrderEntity orderEntity, String orderProductionsID,
			String orderProductionsCount) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		SysUserEntity userEntity = SysUserDao.queryObject(orderEntity.getUserId());
		Long useIntegralCount = orderEntity.getUserIntegralCount();
		
		String[] orderProductionsIDs = orderProductionsID.split(",");
		String[] orderProductionsCounts = orderProductionsCount.split(",");
		
		//减库存
		for(int i =0;i<orderProductionsIDs.length;i++){
			Long store = productDao.apiQueryStore(orderProductionsIDs[i],orderProductionsCounts[i]);
			if(store!=null){
				Long remainderStore = store - Long.valueOf(orderProductionsCounts[i]);
				Integer k = productDao.apiMinusStore(orderProductionsIDs[i],remainderStore);
			}else{				
				return R.error("商品库存不足！");
			}
			
		}		
		
		if(orderEntity.getOrderAllPrice().equals(0)){//积分付款
		
			SysUserDao.addIntegral(userEntity.getUserIntegral()-useIntegralCount, userEntity.getUserId());
			return R.ok("支付成功！");
						
		}else{//全额付款  +  金额、积分付款    并反积分，积分只反支付金额的那部分
			
			//说明使用 金额、积分付款
			//注释原因：支付成功在扣积分  在notify方法中扣
			/*if(useIntegralCount>0){
				SysUserDao.addIntegral(userEntity.getUserIntegral()-useIntegralCount, userEntity.getUserId());
			}*/
			
		    Map<String, String> param = new HashMap<>();
	        // 公共请求参数
	        param.put("app_id", AlipayUtil.ALIPAY_APPID);// 商户订单号
	        param.put("method", "alipay.trade.app.pay");// 交易金额
	        param.put("format", AlipayConstants.FORMAT_JSON);
	        param.put("charset", AlipayConstants.CHARSET_UTF8);
	        param.put("timestamp", DatetimeUtil.formatDateTime(new Date()));
	        param.put("version", "1.0");
	        param.put("notify_url", "http:59.110.163.137/api/notify"); // 支付宝服务器主动通知商户服务
	        param.put("sign_type", AlipayConstants.SIGN_TYPE_RSA);

	        Map<String, Object> pcont = new HashMap<>();
	        // 支付业务请求参数
	        String tradeNO = orderEntity.getOrderId();
	        pcont.put("out_trade_no", tradeNO); // 商户订单号
	        pcont.put("total_amount", orderEntity.getOrderAllPrice());// 交易金额
	        pcont.put("subject", "标题"); // 订单标题
	        pcont.put("body", "商品描述");// 对交易或商品的描述
	        pcont.put("product_code", "QUICK_MSECURITY_PAY");// 销售产品码
	        
	        try {
	            param.put("passback_params", URLEncoder.encode(String.valueOf(useIntegralCount), "UTF-8"));
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        
	        param.put("biz_content", new Gson().toJson(pcont)); // 业务请求参数  不需要对json字符串转义


	        Map<String, Object> payMap = new HashMap<>();
	        try {
	            param.put("sign", PayUtil.getSign(param, AlipayUtil.APP_PRIVATE_KEY)); // 业务请求参数
	            payMap.put("orderStr", PayUtil.getSignEncodeUrl(param, true));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
			 return R.ok(payMap);
		}			
	}
	
	
	@Override
	public void dispatch(String orderId,String userId){
		
		OrderEntity order = orderDao.queryObject(orderId);
		SysUserEntity userEntity = SysUserDao.queryObject(Long.valueOf(userId));
		order.setDeliveryUserId(Long.valueOf(userId));
		order.setDeliveryUserName(userEntity.getUsername());
		order.setIsRebate(0);	
		order.setOrderType(EnumOrderType.DISPATCHING.getStatus());
		orderDao.update(order);
		
	}
	
	@Override
	public void complete(String orderId){
		
		OrderEntity order = orderDao.queryObject(orderId);
		
		//写入积分明显表
		OrderintegrationEntity orderintegration = new OrderintegrationEntity();
		orderintegration.setUserId(order.getUserId());
		orderintegration.setOrderId(orderId);
		orderintegration.setOrderSumPrice(order.getOrderAllPrice());
		
		//计算得到相应积分
		SysUserEntity userEntity = SysUserDao.queryObject(order.getDeliveryUserId());
		ConfigtableEntity configtableEntity = configtableDao.getConfig(userEntity);			
		//得到比例，算的积分
		String proportion = configtableEntity.getConfigValue();
		Long thisIntegral = order.getOrderAllPrice().longValue()*Long.valueOf(proportion); 	
		
		orderintegration.setIntegration(thisIntegral);
		orderintegration.setPriceIntegrationType(1);//2销售积分 1配送积分			
		//注意：这里设计的是给配送人员用的，是否兑换过
		orderintegration.setIsRebate(0); //0未兑换过，1兑换过
		orderintegration.setTime(new Date());
		orderintegrationDao.save(orderintegration);
		
		//订单完成
		order.setOrderType(EnumOrderType.DISPATCHING.getStatus());
		orderDao.update(order);
	}
}
