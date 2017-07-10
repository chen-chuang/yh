package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.modules.yh.dao.EnterpriseinfoDao;
import io.renren.modules.yh.entity.EnterpriseinfoEntity;
import io.renren.modules.yh.service.EnterpriseinfoService;



@Service("enterpriseinfoService")
public class EnterpriseinfoServiceImpl implements EnterpriseinfoService {
	@Autowired
	private EnterpriseinfoDao enterpriseinfoDao;
	
	@Override
	public EnterpriseinfoEntity queryObject(Long enterpriseId){
		return enterpriseinfoDao.queryObject(enterpriseId);
	}
	
	@Override
	public List<EnterpriseinfoEntity> queryList(Map<String, Object> map){
		return enterpriseinfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return enterpriseinfoDao.queryTotal(map);
	}
	
	@Override
	public void save(EnterpriseinfoEntity enterpriseinfo){
		enterpriseinfoDao.save(enterpriseinfo);
	}
	
	@Override
	public void update(EnterpriseinfoEntity enterpriseinfo){
		enterpriseinfoDao.update(enterpriseinfo);
	}
	
	@Override
	public void delete(Long enterpriseId){
		enterpriseinfoDao.delete(enterpriseId);
	}
	
	@Override
	public void deleteBatch(Long[] enterpriseIds){
		enterpriseinfoDao.deleteBatch(enterpriseIds);
	}
	
}
