package io.renren.modules.yh.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.api.entity.dto.OrderInfo;
import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.yh.entity.OrderEntity;

/**
 * 订单表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {

	List<OrderDetailInfo> apiOrderList(Map<String, Object> map);

	List<OrderDetailInfo> apiQueryOrder(@Param("startTime") Date dStartTime, @Param("endTime") Date dEndTime,  @Param("townID") String townID);

	void apiDelWaitingPayOrder(@Param("userID") String userID, @Param("orderID") String orderID);
	
}
