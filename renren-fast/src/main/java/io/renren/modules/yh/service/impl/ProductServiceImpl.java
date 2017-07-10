package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.modules.yh.dao.ProductDao;
import io.renren.modules.yh.entity.ProductEntity;
import io.renren.modules.yh.service.ProductService;



@Service("productService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Override
	public ProductEntity queryObject(Long productId){
		return productDao.queryObject(productId);
	}
	
	@Override
	public List<ProductEntity> queryList(Map<String, Object> map){
		return productDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return productDao.queryTotal(map);
	}
	
	@Override
	public void save(ProductEntity product){
		productDao.save(product);
	}
	
	@Override
	public void update(ProductEntity product){
		productDao.update(product);
	}
	
	@Override
	public void delete(Long productId){
		productDao.delete(productId);
	}
	
	@Override
	public void deleteBatch(Long[] productIds){
		productDao.deleteBatch(productIds);
	}
	
}
