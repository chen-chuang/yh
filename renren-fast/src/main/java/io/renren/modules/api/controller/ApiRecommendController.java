package io.renren.modules.api.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.AppValidateUtils;
import io.renren.common.utils.CommonUtils;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.ProductTypeDTO;
import io.renren.modules.api.entity.dto.ShoppingCartDTO;
import io.renren.modules.api.entity.dto.TownDTO;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.entity.enums.EnumOrderType;
import io.renren.modules.yh.service.CollectionService;
import io.renren.modules.yh.service.OrderService;
import io.renren.modules.yh.service.ProductService;
import io.renren.modules.yh.service.ProducttypeService;
import io.renren.modules.yh.service.RegionService;
import sun.reflect.generics.tree.Tree;

@RequestMapping("/api")
@RestController
public class ApiRecommendController {
	
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
		
		OrderEntity orderEntity = new OrderEntity();
		
		orderEntity.setOrderCreateTime(new Date());
		orderEntity.setOrderId(CommonUtils.generateOrder());
		orderEntity.setOrderType(EnumOrderType.TOBEPAID.getStatus());//默认待支付
		
		
		orderEntity.setUserId(Long.valueOf(userId));
		orderEntity.setOrderAddress(orderAddress);
		orderEntity.setTownId(Integer.valueOf(townId));
		orderEntity.setOrderDetailAddress(orderDetailAddress);
		orderEntity.setOrderSendTime(DateUtils.parse(orderSendTime, DateUtils.DATE_TIME_PATTERN));
		orderEntity.setReceiverPhone(receiverPhone);
		orderEntity.setReceiverName(receiverName);
		orderEntity.setMark(mark);
		orderEntity.setUserIntegralCount(Long.valueOf(useIntegralCount));
		orderEntity.setOrderPayType(Integer.valueOf(orderPayType));
		orderEntity.setOrderAllPrice(new BigDecimal(orderAllPrice));
		
		Map<String, Object> info = new HashMap<String, Object>();
		
		info = orderService.apiSubmitOrder(orderEntity,orderProductionsID,orderProductionsCount);
		
		return R.ok();
	}
	
	@AuthIgnore
	@RequestMapping("hotSaleProduction")
	public R hotSaleProduction(HttpServletRequest request,@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		/*String areaID = request.getParameter("areaID");
		
		String appVersion = request.getParameter("appVersion");
		String systemVersion = request.getParameter("systemVersion");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String platform = request.getParameter("platform");
		String systemType = request.getParameter("systemType");
		String requestTime = request.getParameter("requestTime");
		String token = request.getParameter("token");
		String uuid = request.getParameter("uuid");
		String sign = request.getParameter("sign");
		
		Map<String, String> map= new TreeMap<String, String>();
		map.put("areaID", areaID);
		map.put("appVersion",appVersion );
		map.put("systemVersion", systemVersion);
		map.put("latitude",latitude );
		map.put("longitude",longitude );
		map.put("platform",platform );
		map.put("systemType",systemType );
		map.put("requestTime",requestTime );
		map.put("token",token );
		map.put("uuid",uuid );*/
		
		String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			List<CollectionDTO> info = productService.apiHotSaleProduction(map.get("areaID"));
			return R.ok().put("info", info);
		}else{
			return R.ok();
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
			List<ShoppingCartDTO> info = productService.apiShoppingCartList(map.get("userID"),map.get("areaID"));
		    return R.ok().put("info", info);
		}else{
			return R.ok();
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
			return R.ok();
		}	
	}
	
	@AuthIgnore
	@RequestMapping("collectProduction")
	public R collectProduction(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			collectionService.apiCollectProduction(map.get("userID"),map.get("productId"),map.get("isCollected"));
			return R.ok();
		}else{
			return R.error();
		}
	}
	
	
	

}
