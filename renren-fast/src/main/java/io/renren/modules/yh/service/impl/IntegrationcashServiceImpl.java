package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.modules.yh.dao.IntegrationcashDao;
import io.renren.modules.yh.entity.IntegrationcashEntity;
import io.renren.modules.yh.service.IntegrationcashService;



@Service("integrationcashService")
public class IntegrationcashServiceImpl implements IntegrationcashService {
	@Autowired
	private IntegrationcashDao integrationcashDao;
	
	@Override
	public IntegrationcashEntity queryObject(Integer id){
		return integrationcashDao.queryObject(id);
	}
	
	@Override
	public List<IntegrationcashEntity> queryList(Map<String, Object> map){
		return integrationcashDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return integrationcashDao.queryTotal(map);
	}
	
	@Override
	public void save(IntegrationcashEntity integrationcash){
		integrationcashDao.save(integrationcash);
	}
	
	@Override
	public void update(IntegrationcashEntity integrationcash){
		integrationcashDao.update(integrationcash);
	}
	
	@Override
	public void delete(Integer id){
		integrationcashDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		integrationcashDao.deleteBatch(ids);
	}
	
}
