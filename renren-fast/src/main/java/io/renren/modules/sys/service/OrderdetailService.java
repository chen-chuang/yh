package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.OrderdetailEntity;

/**
 * 订单明细表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
public interface OrderdetailService {
	
	OrderdetailEntity queryObject(String orderId);
	
	List<OrderdetailEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(OrderdetailEntity orderdetail);
	
	void update(OrderdetailEntity orderdetail);
	
	void delete(String orderId);
	
	void deleteBatch(String[] orderIds);
}
