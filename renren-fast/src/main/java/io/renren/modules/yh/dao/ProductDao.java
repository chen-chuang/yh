package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.ProductEntity;
import io.renren.modules.yh.entity.ProducttypeEntity;
import io.renren.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

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
	
}
