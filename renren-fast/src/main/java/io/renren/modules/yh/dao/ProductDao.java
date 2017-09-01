package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.ProductEntity;
import io.renren.modules.yh.entity.ProducttypeEntity;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.EnterpriseProductions;
import io.renren.modules.api.entity.dto.ShoppingCartDTO;
import io.renren.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 产品表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface ProductDao extends BaseDao<ProductEntity> {

	void deleteBatchByType(Integer[] ids);

	void updateProductType(ProducttypeEntity producttype);

	List<CollectionDTO> apiHotSaleProduction(@Param("areaID")String areaID, @Param("userID")String userID);

	List<CollectionDTO> apiSearchProduction(@Param("keyword")String keyword, @Param("areaID")String areaID, @Param("userID")String userID);

    Long apiQueryStore(@Param("orderProductionsID")String orderProductionsID, @Param("orderProductionsCount")String orderProductionsCount);

	Integer apiMinusStore(@Param("orderProductionsID")String orderProductionsID, @Param("remainderStore")Long remainderStore);

	List<ShoppingCartDTO> apiShoppingCartList(@Param("userId")String userId, @Param("areaID")String areaID);

	List<EnterpriseProductions> apiEnterpriseProducts(Map<String, Object> map);

	List<ShoppingCartDTO> apiShoppingCartPFList(@Param("userId")String userId, @Param("areaID")String areaID);

	List<ProductEntity> queryPcList(Map<String, Object> map);

	int queryPcTotal(Map<String, Object> map);
	
}
