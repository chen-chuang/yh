package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.renren.modules.yh.dao.RegionDao;
import io.renren.modules.yh.entity.RegionEntity;
import io.renren.modules.yh.service.RegionService;



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

	@Override
	public List<RegionEntity> queryListByPid(int pid) {
		
		return regionDao.queryListByPid(pid);
	}

	@Override
	public String getRegionFullName(int regionId) {
		return null;
	}
	
	@Override
	public List<String> getFullRegion(int id){
		List<String> full = new ArrayList<String>();
		full.add(String.valueOf(id));
		getParentListById(id,full);
		Collections.reverse(full);
		return full;
	}
	
	public List<String> getParentListById(int id,List<String> full){
		Map<String,Object> parentInfo = regionDao.getParentById(id);
		if(!parentInfo.get("pid").equals(0)){
		  full.add(String.valueOf(parentInfo.get("pid")));
		  getParentListById(Integer.parseInt(String.valueOf(parentInfo.get("pid"))),full);
		}
		return full;
	}
	
	public Map<String,Object> getParentById(int id){
		
		Map<String,Object> parentInfo = regionDao.getParentById(id);
		return parentInfo;
		
	}
	
}
