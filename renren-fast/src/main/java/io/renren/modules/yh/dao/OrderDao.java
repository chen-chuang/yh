package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {
	
}
