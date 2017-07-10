package io.renren.modules.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.sys.dao.RegionDao;
import io.renren.modules.sys.entity.RegionEntity;
import io.renren.modules.sys.service.RegionService;




@Service("regionService")
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionDao regionDao;
	
	@Override
	public RegionEntity queryObject(Integer id){
		return regionDao.queryObject(id);
	}
	
	@Override
	public List<RegionEntity> queryList(Map<String, Object> map){
		return regionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return regionDao.queryTotal(map);
	}
	
	@Override
	public void save(RegionEntity region){
		regionDao.save(region);
	}
	
	@Override
	public void update(RegionEntity region){
		regionDao.update(region);
	}
	
	@Override
	public void delete(Integer id){
		regionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		regionDao.deleteBatch(ids);
	}
	
}
