package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.CollectionEntity;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.sys.dao.BaseDao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-24 14:18:20
 */
@Mapper
public interface CollectionDao extends BaseDao<CollectionEntity> {

	List<CollectionDTO> apiQueryCollectionList(Map<String, Object> map);
	
	void apiCollectProductionInsert(@Param("userID")String userID, @Param("productId")String productId, @Param("date")Date date);

	void apiCollectProductionDelete(@Param("userID")String userID, @Param("productId")String productId);
	
}
