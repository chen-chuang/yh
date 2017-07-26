package io.renren.modules.api.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.CommonUtils;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.TownDTO;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.entity.enums.EnumOrderType;
import io.renren.modules.yh.service.OrderService;
import io.renren.modules.yh.service.ProductService;
import io.renren.modules.yh.service.RegionService;

@RequestMapping("/api")
@RestController
public class ApiRecommendController {
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@AuthIgnore
	@RequestMapping("town")
	public R town(HttpServletRequest request){
		
		String areaID = request.getParameter("areaID");
		List<TownDTO> region = regionService.apiTown(areaID);
		
		return R.ok().put("info", region);
		
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
	public R hotSaleProduction(String areaID){
		List<CollectionDTO> info = productService.apiHotSaleProduction(areaID);
		return R.ok().put("info", info);
	}
	
	@AuthIgnore
	@RequestMapping("searchProduction")
	public R searchProduction(String keyword,String areaID){
		
		List<CollectionDTO> info = productService.apiSearchProduction(keyword,areaID);
		
		return R.ok().put("info", info);
	}
	
	@AuthIgnore
	@RequestMapping("shoppingCartList")
	public R shoppingCartList(String userID,String areaID){
		
		//productService.apiShoppingCartList(userID,areaID);
		return R.ok();
	}
	

}
