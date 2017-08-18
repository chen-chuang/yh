package io.renren.modules.yh.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.api.entity.dto.EnterpriseDeatailInfoDTO;
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
	
	@Override
	public List<EnterpriseinfoEntity> apiQueryList(Map<String, Object> map){
		return enterpriseinfoDao.apiQueryList(map);
	}
	
	@Override
	public EnterpriseDeatailInfoDTO apiEnterpriseByID(String enterpriseId){
		return enterpriseinfoDao.apiEnterpriseByID(enterpriseId); 
	}
	
	@Override
	public List<Map<String, Object>> getEnterprise(){
		return enterpriseinfoDao.getEnterprise();
	}
	
	@Override
	public List<Map<String, Object>> getByName(String enterpriseName){
		return enterpriseinfoDao.getByName(enterpriseName);
	}
	
	@Override
	public int validateOnlyAgency(String enterpriseAreaId, String userId, Integer enterpriseType){
		return enterpriseinfoDao.validateOnlyAgency(enterpriseAreaId,userId,enterpriseType);
	}
	
}
