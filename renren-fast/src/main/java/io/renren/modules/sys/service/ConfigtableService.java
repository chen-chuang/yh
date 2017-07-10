package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.ConfigtableEntity;

/**
 * 配置表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
public interface ConfigtableService {
	
	ConfigtableEntity queryObject(Integer id);
	
	List<ConfigtableEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ConfigtableEntity configtable);
	
	void update(ConfigtableEntity configtable);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
