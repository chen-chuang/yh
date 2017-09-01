package io.renren.modules.yh.service;

import io.renren.common.utils.Query;
import io.renren.modules.yh.entity.OrderdetailEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单明细表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface OrderdetailService {
	
	OrderdetailEntity queryObject(String orderId);
	
	List<OrderdetailEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(OrderdetailEntity orderdetail);
	
	void update(OrderdetailEntity orderdetail);
	
	void delete(String orderId);
	
	void deleteBatch(String[] orderIds);

	List<OrderdetailEntity> queryPcList(Map<String, Object> map);

	int queryPcTotal(Map<String, Object> map);
}
