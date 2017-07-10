package io.renren.modules.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.sys.dao.ConfigtableDao;
import io.renren.modules.sys.entity.ConfigtableEntity;
import io.renren.modules.sys.service.ConfigtableService;




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
	
}
