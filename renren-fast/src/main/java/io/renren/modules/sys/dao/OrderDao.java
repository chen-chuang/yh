package io.renren.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.sys.entity.OrderEntity;

/**
 * 订单表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {
	
}
