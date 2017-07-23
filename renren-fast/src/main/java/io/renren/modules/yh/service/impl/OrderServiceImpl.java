package io.renren.modules.yh.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.common.utils.DateUtils;
import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.api.entity.dto.OrderProductions;
import io.renren.modules.yh.dao.OrderDao;
import io.renren.modules.yh.dao.OrderdetailDao;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.service.OrderService;



@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderdetailDao orderdetailDao;
	
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
	
}
