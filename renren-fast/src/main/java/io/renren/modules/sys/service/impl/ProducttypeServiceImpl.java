package io.renren.modules.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.sys.dao.ProducttypeDao;
import io.renren.modules.sys.entity.ProducttypeEntity;
import io.renren.modules.sys.service.ProducttypeService;




@Service("producttypeService")
public class ProducttypeServiceImpl implements ProducttypeService {
	@Autowired
	private ProducttypeDao producttypeDao;
	
	@Override
	public ProducttypeEntity queryObject(Integer id){
		return producttypeDao.queryObject(id);
	}
	
	@Override
	public List<ProducttypeEntity> queryList(Map<String, Object> map){
		return producttypeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return producttypeDao.queryTotal(map);
	}
	
	@Override
	public void save(ProducttypeEntity producttype){
		producttypeDao.save(producttype);
	}
	
	@Override
	public void update(ProducttypeEntity producttype){
		producttypeDao.update(producttype);
	}
	
	@Override
	public void delete(Integer id){
		producttypeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		producttypeDao.deleteBatch(ids);
	}
	
}
