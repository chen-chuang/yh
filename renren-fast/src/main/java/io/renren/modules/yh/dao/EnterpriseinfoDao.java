package io.renren.modules.yh.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.renren.modules.api.entity.dto.EnterpriseDeatailInfoDTO;
import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.yh.entity.EnterpriseinfoEntity;
import io.renren.modules.yh.entity.RegionEntity;

/**
 * 企业信息表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface EnterpriseinfoDao extends BaseDao<EnterpriseinfoEntity> {

	List<EnterpriseinfoEntity> apiQueryList(Map<String, Object> map);
	
	EnterpriseDeatailInfoDTO apiEnterpriseByID(String enterpriseId);

	List<Map<String, Object>> getEnterprise();

	List<Map<String, Object>> getByName(String enterpriseName);

	int updateRegion(RegionEntity region);

	int isExistByRegion(Integer id);

	List<Map<String, String>> apiEnterpriseCity(int type);

	int validateOnlyAgency(@Param("areaId")String enterpriseAreaId, 
			@Param("userId")String userId, @Param("enterpriseType")Integer enterpriseType, @Param("enterpriseId")Long enterpriseId); 
	
}
