package io.renren.modules.yh.service;

import io.renren.modules.yh.entity.OrderintegrationEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单积分表（销售人员、配送员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface OrderintegrationService {
	
	OrderintegrationEntity queryObject(Integer id);
	
	List<OrderintegrationEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(OrderintegrationEntity orderintegration);
	
	void update(OrderintegrationEntity orderintegration);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	void rebate(String startTime, String endTime, String deliveryUserId);

	void rebateByIds(Integer[] ids);
}
