package io.renren.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.sys.entity.OrderintegrationEntity;

/**
 * 订单积分表（销售人员、配送员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
@Mapper
public interface OrderintegrationDao extends BaseDao<OrderintegrationEntity> {
	
}
