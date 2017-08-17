package io.renren.modules.yh.service.impl;

import org.apache.http.conn.util.PublicSuffixList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import io.renren.modules.api.entity.dto.EnterpriseProductions;
import io.renren.modules.api.entity.dto.ProductTypeDTO;
import io.renren.modules.yh.dao.ProductDao;
import io.renren.modules.yh.dao.ProducttypeDao;
import io.renren.modules.yh.entity.ProducttypeEntity;
import io.renren.modules.yh.service.ProducttypeService;



@Service("producttypeService")
public class ProducttypeServiceImpl implements ProducttypeService {
	@Autowired
	private ProducttypeDao producttypeDao;
	
	@Autowired
	private ProductDao productDao;
	
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
	@Transactional
	public void deleteBatch(Integer[] ids){
		producttypeDao.deleteBatch(ids);
		productDao.deleteBatchByType(ids);
	}
	
	@Override
	public List<Map<String, Object>> getProductType(Long userId){
		return producttypeDao.getProductType(userId);
	}
	
	@Override
	public int getProductByType(Integer[] ids){
		return producttypeDao.getProductByType(ids);
	}
	
	@Override
	public List<ProductTypeDTO> apiGetCategory(String userID, String areaID){
		return producttypeDao.apiGetCategory(userID, areaID);
	}
}
