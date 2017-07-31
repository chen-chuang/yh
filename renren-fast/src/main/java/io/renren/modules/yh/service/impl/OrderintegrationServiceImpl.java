package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.modules.yh.dao.OrderintegrationDao;
import io.renren.modules.yh.entity.OrderintegrationEntity;
import io.renren.modules.yh.service.OrderintegrationService;



@Service("orderintegrationService")
public class OrderintegrationServiceImpl implements OrderintegrationService {
	@Autowired
	private OrderintegrationDao orderintegrationDao;
	
	@Override
	public OrderintegrationEntity queryObject(Integer id){
		return orderintegrationDao.queryObject(id);
	}
	
	@Override
	public List<OrderintegrationEntity> queryList(Map<String, Object> map){
		return orderintegrationDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return orderintegrationDao.queryTotal(map);
	}
	
	@Override
	public void save(OrderintegrationEntity orderintegration){
		orderintegrationDao.save(orderintegration);
	}
	
	@Override
	public void update(OrderintegrationEntity orderintegration){
		orderintegrationDao.update(orderintegration);
	}
	
	@Override
	public void delete(Integer id){
		orderintegrationDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		orderintegrationDao.deleteBatch(ids);
	}

	@Override
	public void rebate(String startTime, String endTime, String deliveryUserId) {
		orderintegrationDao.rebate(startTime, endTime, deliveryUserId);		
	}

	@Override
	public void rebateByIds(Integer[] ids) {
		orderintegrationDao.rebateByIds(ids);
	}

	@Override
	public Map<String, Object> rebateDetailByIds(Integer[] ids) {
		Map<String, Object> map = orderintegrationDao.rebateDetailByIds(ids);
		return map;
		
	}

	@Override
	public Map<String, Object> rebateDetail(String startTime, String endTime, String deliveryUserId) {
		return orderintegrationDao.rebateDetail(startTime, endTime, deliveryUserId);
	}
	
}
