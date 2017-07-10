package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.modules.yh.dao.OrderDao;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.service.OrderService;



@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	
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
	
}
