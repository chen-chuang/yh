package io.renren.modules.yh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.ConfigtableEntity;

/**
 * 配置表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:49:26
 */
@Mapper
public interface ConfigtableDao extends BaseDao<ConfigtableEntity> {

	/**
	 * 用户积分配置查询
	 * @param user
	 */
	ConfigtableEntity getConfig(SysUserEntity user);
	
}
