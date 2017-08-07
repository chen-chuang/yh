package io.renren.modules.yh.service;

import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.ShoppingCartDTO;
import io.renren.modules.yh.entity.ProductEntity;

import java.util.List;
import java.util.Map;

/**
 * 产品表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface ProductService {
	
	ProductEntity queryObject(Long productId);
	
	List<ProductEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProductEntity product);
	
	void update(ProductEntity product);
	
	void delete(Long productId);
	
	void deleteBatch(Long[] productIds);

	List<CollectionDTO> apiHotSaleProduction(String areaID, String userID);

	List<CollectionDTO> apiSearchProduction(String keyword, String areaID, String userID);

	List<ShoppingCartDTO> apiShoppingCartList(String userID, String areaID);
}
