package io.renren.modules.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.sys.dao.OrderdetailDao;
import io.renren.modules.sys.entity.OrderdetailEntity;
import io.renren.modules.sys.service.OrderdetailService;




@Service("orderdetailService")
public class OrderdetailServiceImpl implements OrderdetailService {
	@Autowired
	private OrderdetailDao orderdetailDao;
	
	@Override
	public OrderdetailEntity queryObject(String orderId){
		return orderdetailDao.queryObject(orderId);
	}
	
	@Override
	public List<OrderdetailEntity> queryList(Map<String, Object> map){
		return orderdetailDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return orderdetailDao.queryTotal(map);
	}
	
	@Override
	public void save(OrderdetailEntity orderdetail){
		orderdetailDao.save(orderdetail);
	}
	
	@Override
	public void update(OrderdetailEntity orderdetail){
		orderdetailDao.update(orderdetail);
	}
	
	@Override
	public void delete(String orderId){
		orderdetailDao.delete(orderId);
	}
	
	@Override
	public void deleteBatch(String[] orderIds){
		orderdetailDao.deleteBatch(orderIds);
	}
	
}
