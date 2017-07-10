package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.ProducttypeEntity;

/**
 * 产品分类表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
public interface ProducttypeService {
	
	ProducttypeEntity queryObject(Integer id);
	
	List<ProducttypeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProducttypeEntity producttype);
	
	void update(ProducttypeEntity producttype);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
