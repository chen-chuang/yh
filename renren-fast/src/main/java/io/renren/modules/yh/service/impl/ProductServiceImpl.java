package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.EnterpriseProductions;
import io.renren.modules.api.entity.dto.ShoppingCartDTO;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.dao.ProductDao;
import io.renren.modules.yh.entity.ProductEntity;
import io.renren.modules.yh.entity.enums.EnumPermission;
import io.renren.modules.yh.service.ProductService;



@Service("productService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	
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
	
	@Override
	public List<CollectionDTO> apiHotSaleProduction(String areaID, String userID){
		return productDao.apiHotSaleProduction(areaID, userID);
	}
	
	@Override
	public List<CollectionDTO> apiSearchProduction(String keyword, String areaID,String userID){
		return productDao.apiSearchProduction(keyword, areaID,userID);
	}
	
	@Override
	public List<ShoppingCartDTO> apiShoppingCartList(String userID, String areaID){
		SysUserEntity userEntity = sysUserDao.queryObject(userID);
		if(userEntity.getUserPermission().equals(EnumPermission.SALE.getType())){
			return productDao.apiShoppingCartList(userID, areaID);
		}else{
			return productDao.apiShoppingCartPFList(userID, areaID);
		}
		
	}	
	
	@Override
	public List<EnterpriseProductions> apiEnterpriseProducts(Map<String, Object> map){
		return productDao.apiEnterpriseProducts(map);
	}
	
}
