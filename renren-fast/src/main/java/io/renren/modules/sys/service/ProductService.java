package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.ProductEntity;

/**
 * 产品表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
public interface ProductService {
	
	ProductEntity queryObject(Long productId);
	
	List<ProductEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProductEntity product);
	
	void update(ProductEntity product);
	
	void delete(Long productId);
	
	void deleteBatch(Long[] productIds);
}
