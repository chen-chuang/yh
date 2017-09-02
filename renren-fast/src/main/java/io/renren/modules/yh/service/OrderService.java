package io.renren.modules.yh.service;

import java.util.List;
import java.util.Map;

import io.renren.common.utils.R;
import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.entity.dto.DeliveryOrderDTO;

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

	List<OrderDetailInfo> apiOrderList(Map<String, Object> params);

	List<OrderDetailInfo> apiQueryOrder(String startTime, String endTime, String townID, String userID);

	R apiSubmitOrder(OrderEntity orderEntity, String orderProductionsID, String orderProductionsCount);

	R dispatch(String orderId);
	
	void complete(String orderId);

	void apiDelWaitingPayOrder(String userID, String orderID);

	DeliveryOrderDTO printDelivery(String orderId);

	R pcCreateOrdere(OrderEntity order);

	R pcDispatch(String orderId);

	void pcComplete(String orderId);
}
