package io.renren.modules.yh.service;

import io.renren.common.utils.Query;
import io.renren.modules.api.entity.dto.EnterpriseDeatailInfoDTO;
import io.renren.modules.yh.entity.EnterpriseinfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 企业信息表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface EnterpriseinfoService {
	
	EnterpriseinfoEntity queryObject(Long enterpriseId);
	
	List<EnterpriseinfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(EnterpriseinfoEntity enterpriseinfo);
	
	void update(EnterpriseinfoEntity enterpriseinfo);
	
	void delete(Long enterpriseId);
	
	void deleteBatch(Long[] enterpriseIds);

	List<EnterpriseinfoEntity> apiQueryList(Map<String, Object> map);
	
	EnterpriseDeatailInfoDTO apiEnterpriseByID(String enterpriseId);

	List<Map<String, Object>> getEnterprise();

	List<Map<String, Object>> getByName(String enterpriseName);

	int validateOnlyAgency(String enterpriseAreaId, String userId, Integer enterpriseType);
}
