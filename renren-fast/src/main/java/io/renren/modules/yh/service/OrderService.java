package io.renren.modules.yh.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.yh.entity.OrderEntity;

/**
 * 订单表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface OrderService {
	
	OrderEntity queryObject(String orderId);
	
	List<OrderEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(OrderEntity order);
	
	void update(OrderEntity order);
	
	void delete(String orderId);
	
	void deleteBatch(String[] orderIds);

	List<OrderDetailInfo> apiOrderList(String userID, String orderType);

	List<OrderDetailInfo> apiQueryOrder(String startTime, String endTime, String townID);

	Map<String, Object> apiSubmitOrder(OrderEntity orderEntity, String orderProductionsID, String orderProductionsCount);

	void dispatch(String orderId, String userId);
	
	void complete(String orderId);
}
