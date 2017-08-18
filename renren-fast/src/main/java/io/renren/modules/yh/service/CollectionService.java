package io.renren.modules.yh.service;

import io.renren.common.utils.Query;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.yh.entity.CollectionEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-24 14:18:20
 */
public interface CollectionService {
	
	CollectionEntity queryObject(Long userId);
	
	List<CollectionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CollectionEntity collection);
	
	void update(CollectionEntity collection);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

	List<CollectionDTO> apiQueryCollectionList(Map<String, Object> map);

	void apiCollectProduction(String userID, String productId, String isCollected, Date date);
}
