package io.renren.modules.yh.service;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.ConfigtableEntity;

import java.util.List;
import java.util.Map;

/**
 * 配置表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:49:26
 */
public interface ConfigtableService {
	
	ConfigtableEntity queryObject(Integer id);
	
	List<ConfigtableEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ConfigtableEntity configtable);
	
	void update(ConfigtableEntity configtable);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	ConfigtableEntity getConfigIntegerationCash(SysUserEntity user);

	ConfigtableEntity getConfig(SysUserEntity userEntity);

	String apiPriceLimit(String userID, String areaID);
}
