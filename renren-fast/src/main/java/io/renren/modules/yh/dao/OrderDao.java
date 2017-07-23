package io.renren.modules.yh.dao;

import java.util.List;

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

	List<OrderDetailInfo> apiOrderList(@Param("userID") String userID, @Param("orderType") String orderType);
	
}
