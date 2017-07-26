package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.ProductEntity;
import io.renren.modules.yh.entity.ProducttypeEntity;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.sys.dao.BaseDao;

import java.util.List;

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

	List<CollectionDTO> apiHotSaleProduction(String areaID);

	List<CollectionDTO> apiSearchProduction(@Param("keyword")String keyword, @Param("areaID")String areaID);

    Long apiQueryStore(@Param("orderProductionsID")String orderProductionsID, @Param("orderProductionsCount")String orderProductionsCount);

	int apiMinusStore(String orderProductionsID, Long remainderStore);
	
}
