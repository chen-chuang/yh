package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.ProducttypeEntity;
import io.renren.modules.api.entity.dto.EnterpriseProductions;
import io.renren.modules.api.entity.dto.ProductTypeDTO;
import io.renren.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 产品分类表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface ProducttypeDao extends BaseDao<ProducttypeEntity> {

	List<Map<String, Object>> getProductType(Long userId);

	int getProductByType(Integer[] id);

	List<ProductTypeDTO> apiGetCategory(@Param("userID")String userID, @Param("areaID")String areaID);
	
}
