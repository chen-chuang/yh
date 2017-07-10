package io.renren.modules.yh.service;

import io.renren.modules.yh.entity.CcountEntity;

import java.util.List;
import java.util.Map;

/**
 * 资金账户表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface CcountService {
	
	CcountEntity queryObject(Integer id);
	
	List<CcountEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CcountEntity ccount);
	
	void update(CcountEntity ccount);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
