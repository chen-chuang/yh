package io.renren.modules.sys.service;
import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.IntegrationcashEntity;


/**
 * 积分兑现表（销售人员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
public interface IntegrationcashService {
	
	IntegrationcashEntity queryObject(Integer id);
	
	List<IntegrationcashEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(IntegrationcashEntity integrationcash);
	
	void update(IntegrationcashEntity integrationcash);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
