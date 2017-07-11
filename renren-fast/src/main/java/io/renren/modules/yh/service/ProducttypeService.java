package io.renren.modules.yh.service;

import io.renren.modules.yh.entity.ProducttypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 产品分类表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
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