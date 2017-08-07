package io.renren.modules.yh.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.renren.common.utils.DateUtils;
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
	public Map<String, Object> apiSubmitOrder(OrderEntity orderEntity, String orderProductionsID,
			String orderProductionsCount) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String[] orderProductionsIDs = orderProductionsID.split(",");
		String[] orderProductionsCounts = orderProductionsCount.split(",");
		
		//减库存
		for(int i =0;i<orderProductionsIDs.length;i++){
			Long store = productDao.apiQueryStore(orderProductionsIDs[i],orderProductionsCounts[i]);
			if(store!=null){
				Long remainderStore = store - Long.valueOf(orderProductionsCounts[i]);
				Integer k = productDao.apiMinusStore(orderProductionsIDs[i],remainderStore);
			}else{
				map.put("msg", "商品库存不足！");
				return map;
			}
			
		}
		//支付还未
		orderEntity.setOrderType(EnumOrderType.PAID.getStatus());//支付完成：已支付
		
		//记得写销售员积分
		SysUserEntity userEntity = SysUserDao.queryObject(orderEntity.getUserId());
		ConfigtableEntity configtableEntity = configtableDao.getConfig(userEntity);
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
			orderintegrationDao.save(orderintegration);
			
			//相加后积分
			Long addIntegral = thisIntegral + userEntity.getUserIntegral();
			
			SysUserDao.addIntegral(addIntegral,userEntity.getUserId());
		}
		
		return map;
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
