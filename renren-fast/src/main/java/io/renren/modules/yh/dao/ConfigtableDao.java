package io.renren.modules.yh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * 得到积分兑现的配置（后加的 不想和getConfig混到一块了）
	 *@描述
	 *@param user
	 *@return
	 *@作者 ccchen
	 *@时间 2017年8月1日下午7:12:35
	 */
	ConfigtableEntity getConfigIntegerationCash(SysUserEntity user);

	String apiPriceLimit(@Param("userID")String userID, @Param("areaID")String areaID);

	int validateOnly(@Param("configKey")String configKey, @Param("userId")Long userId);
	
}
