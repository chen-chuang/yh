package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.OrderintegrationEntity;
import io.renren.modules.sys.dao.BaseDao;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单积分表（销售人员、配送员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface OrderintegrationDao extends BaseDao<OrderintegrationEntity> {

	void rebate(@Param("startTime")Date dStartTime, @Param("endTime")Date dEndTime,@Param("deliveryUserId") String deliveryUserId);

	void rebateByIds(Integer[] ids);
	
	Map<String, Object> rebateDetailByIds(Integer[] id);
	
	Map<String, Object> rebateDetail(@Param("startTime")Date startTime, @Param("endTime")Date endTime,@Param("deliveryUserId") String deliveryUserId);
	
}
