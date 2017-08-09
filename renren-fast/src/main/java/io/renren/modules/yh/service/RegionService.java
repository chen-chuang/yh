package io.renren.modules.yh.service;

import io.renren.modules.api.entity.dto.RegionDTO;
import io.renren.modules.api.entity.dto.TownDTO;
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
	
	String deleteBatch(Integer[] ids);
	
	List<RegionEntity> queryListByPid(int pid);

	/**
	 * 从下往上找
	 * @param id
	 * @return
	 */
	List<String> getFullRegion(int id);

	List<TownDTO> apiTown(String areaID);

	Map<String, Object> getParentKeyValue(String id);

	int onlyId(Integer id);

	List<RegionDTO> apiEnterpriseCity();

	List<RegionDTO> apiEnterpriseProducts();
	
	
}
