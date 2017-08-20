package io.renren.modules.yh.service;

import io.renren.modules.yh.entity.OrderintegrationEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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

	void rebate(Date dStartTime, Date dEndTime, String deliveryUserId, String sumIntegration);

	void rebateByIds(Integer[] ids, String sumIntegration, String deliveryUserId);
	
    Map<String, Object> rebateDetailByIds(Integer[] ids);
	
	Map<String, Object> rebateDetail(Date dStartTime, Date dStartTime2,String deliveryUserId);
}
