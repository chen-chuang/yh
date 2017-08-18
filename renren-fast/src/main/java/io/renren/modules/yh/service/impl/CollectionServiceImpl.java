package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.yh.dao.CollectionDao;
import io.renren.modules.yh.entity.CollectionEntity;
import io.renren.modules.yh.service.CollectionService;



@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {
	@Autowired
	private CollectionDao collectionDao;
	
	@Override
	public CollectionEntity queryObject(Long userId){
		return collectionDao.queryObject(userId);
	}
	
	@Override
	public List<CollectionEntity> queryList(Map<String, Object> map){
		return collectionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return collectionDao.queryTotal(map);
	}
	
	@Override
	public void save(CollectionEntity collection){
		collectionDao.save(collection);
	}
	
	@Override
	public void update(CollectionEntity collection){
		collectionDao.update(collection);
	}
	
	@Override
	public void delete(Long userId){
		collectionDao.delete(userId);
	}
	
	@Override
	public void deleteBatch(Long[] userIds){
		collectionDao.deleteBatch(userIds);
	}
	
	@Override
	public List<CollectionDTO> apiQueryCollectionList(Map<String, Object> map){
		List<CollectionDTO> collectionDTOs = collectionDao.apiQueryCollectionList(map);
		return collectionDTOs;
	}
	
	@Override
	public void apiCollectProduction(String userID, String productId, String isCollected, Date date){
		if(isCollected.equals("1")){
			collectionDao.apiCollectProductionInsert(userID, productId,date);
		}else if(isCollected.equals("0")){
			collectionDao.apiCollectProductionDelete(userID, productId);
		}
		
	}
	
}
