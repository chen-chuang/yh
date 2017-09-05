package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.dao.ConfigtableDao;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.service.ConfigtableService;



@Service("configtableService")
public class ConfigtableServiceImpl implements ConfigtableService {
	@Autowired
	private ConfigtableDao configtableDao;
	
	@Override
	public ConfigtableEntity queryObject(Integer id){
		return configtableDao.queryObject(id);
	}
	
	@Override
	public List<ConfigtableEntity> queryList(Map<String, Object> map){
		return configtableDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return configtableDao.queryTotal(map);
	}
	
	@Override
	public void save(ConfigtableEntity configtable){
		configtableDao.save(configtable);
	}
	
	@Override
	public void update(ConfigtableEntity configtable){
		configtableDao.update(configtable);
	}
	
	@Override
	public void delete(Integer id){
		configtableDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		configtableDao.deleteBatch(ids);
	}
	
	
	@Override
	public ConfigtableEntity getConfigIntegerationCash(SysUserEntity user){
		return configtableDao.getConfigIntegerationCash(user);
	}
	
	@Override
	public ConfigtableEntity getConfig(SysUserEntity userEntity){
		return configtableDao.getConfig(userEntity);
	}
	
	
	@Override
	public String apiPriceLimit(String userID, String areaID){
		return configtableDao.apiPriceLimit(userID,areaID);
	}
	
	@Override
	public int validateOnly(String configKey, Long userId){
		return configtableDao.validateOnly(configKey,userId);
	}
}
