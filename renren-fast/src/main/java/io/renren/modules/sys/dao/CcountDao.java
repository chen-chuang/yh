package io.renren.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import io.renren.modules.sys.entity.CcountEntity;

/**
 * 资金账户表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 15:04:32
 */
@Mapper
public interface CcountDao extends BaseDao<CcountEntity> {
	
}
