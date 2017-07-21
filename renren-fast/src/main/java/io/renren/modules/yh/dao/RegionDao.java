package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.RegionEntity;
import io.renren.modules.api.entity.dto.TownDTO;
import io.renren.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 行政区划表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface RegionDao extends BaseDao<RegionEntity> {

	List<RegionEntity> queryListByPid(int pid);

	Map<String, Object> getParentById(int id);

	List<RegionEntity> getChildrenByPid(int pid);

	List<TownDTO> apiTown(String areaID);
	
}
