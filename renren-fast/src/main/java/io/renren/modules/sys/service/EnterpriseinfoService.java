package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.EnterpriseinfoEntity;

/**
 * 企业信息表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
public interface EnterpriseinfoService {
	
	EnterpriseinfoEntity queryObject(Long enterpriseId);
	
	List<EnterpriseinfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(EnterpriseinfoEntity enterpriseinfo);
	
	void update(EnterpriseinfoEntity enterpriseinfo);
	
	void delete(Long enterpriseId);
	
	void deleteBatch(Long[] enterpriseIds);
}
