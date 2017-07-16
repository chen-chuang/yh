package io.renren.modules.yh.service;

import io.renren.modules.yh.entity.RegionEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface RegionService {
	
	RegionEntity queryObject(Integer id);
	
	List<RegionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RegionEntity region);
	
	void update(RegionEntity region);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	List<RegionEntity> queryListByPid(int pid);

	String getRegionFullName(int regionId);

	/**
	 * 从下往上找
	 * @param id
	 * @return
	 */
	List<String> getFullRegion(int id);
	
	
}
