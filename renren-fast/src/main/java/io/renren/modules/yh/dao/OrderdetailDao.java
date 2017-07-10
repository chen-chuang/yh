package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.OrderdetailEntity;
import io.renren.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface OrderdetailDao extends BaseDao<OrderdetailEntity> {
	
}
