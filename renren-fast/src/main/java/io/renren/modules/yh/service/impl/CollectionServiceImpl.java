package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
	
}
