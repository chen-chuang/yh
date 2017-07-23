package io.renren.modules.yh.service;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.IntegrationcashEntity;

import java.util.List;
import java.util.Map;

/**
 * 积分兑现表（销售人员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface IntegrationcashService {
	
	IntegrationcashEntity queryObject(Integer id);
	
	List<IntegrationcashEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(IntegrationcashEntity integrationcash);
	
	void update(IntegrationcashEntity integrationcash);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	void apply(IntegrationcashEntity integrationcashEntity, SysUserEntity user);

	Map<String, Object> getIntegrationInfo(SysUserEntity user);
}
