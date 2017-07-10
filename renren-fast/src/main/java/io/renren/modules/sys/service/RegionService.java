package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.RegionEntity;

/**
 * 行政区划表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:35
 */
public interface RegionService {
	
	RegionEntity queryObject(Integer id);
	
	List<RegionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RegionEntity region);
	
	void update(RegionEntity region);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
