package io.renren.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.sys.entity.WithdrawEntity;

/**
 * 提现记录表（区域经销商）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
@Mapper
public interface WithdrawDao extends BaseDao<WithdrawEntity> {
	
}
