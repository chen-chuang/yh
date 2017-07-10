package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.OrderEntity;

/**
 * 订单表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
public interface OrderService {
	
	OrderEntity queryObject(String orderId);
	
	List<OrderEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(OrderEntity order);
	
	void update(OrderEntity order);
	
	void delete(String orderId);
	
	void deleteBatch(String[] orderIds);
}
