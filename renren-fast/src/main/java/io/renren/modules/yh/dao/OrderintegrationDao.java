package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.OrderintegrationEntity;
import io.renren.modules.sys.dao.BaseDao;
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

	void rebate(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("deliveryUserId") String deliveryUserId);

	void rebateByIds(Integer[] ids);
	
}
