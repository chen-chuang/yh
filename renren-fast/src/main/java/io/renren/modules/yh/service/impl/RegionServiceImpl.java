package io.renren.modules.yh.service.impl;

import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.yh.dao.EnterpriseinfoDao;
import io.renren.modules.yh.dao.RegionDao;
import io.renren.modules.yh.entity.RegionEntity;
import io.renren.modules.yh.service.RegionService;



@Service("regionService")
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private EnterpriseinfoDao enterpriseinfoDao;
	
	
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
	@Transactional
	public void update(RegionEntity region){
				
		regionDao.update(region);
		
		//修改user表对应的行政区划
		sysUserDao.updateRegion(region);
		//修改企业表对应的行政区划
		enterpriseinfoDao.updateRegion(region);
	}
	
	@Override
	public void delete(Integer id){
		regionDao.delete(id);
	}
	
	@Override
	@Transactional
	public String deleteBatch(Integer[] ids){
		String mString = "删除成功！";
	    Map<String, Boolean> map = new HashMap<String,Boolean>();	
		for(Integer id : ids){
			map = isExistEnterpriseAndUser(id,map);
			if(map.containsKey("enterprise")){
				return "所选区域下有企业存在，不允许删除！";
			}
			if(map.containsKey("user")){
				return "所选区域下有用户存在，不允许删除！";
			}			
			
		}
		regionDao.deleteBatch(ids);
		return mString;
	}

	@Override
	public List<RegionEntity> queryListByPid(int pid) {
		
		return regionDao.queryListByPid(pid);
	}
	
	@Override
    /**
     * 获得<省,市, , ,>这种形式，由下往上
     */
	public List<String> getFullRegion(int id){
		List<String> full = new ArrayList<String>();
		full.add(String.valueOf(id));
		getParentListById(id,full);
		Collections.reverse(full);
		return full;
	}
	
	
	 /**
     * 获得<省,市, , >这种形式，由下往上
     */
	public List<String> getParentListById(int id,List<String> full){
		Map<String,Object> parentInfo = regionDao.getParentById(id);
		if(!parentInfo.get("pid").equals(0)){
		  full.add(String.valueOf(parentInfo.get("pid")));
		  getParentListById(Integer.parseInt(String.valueOf(parentInfo.get("pid"))),full);
		}
		return full;
	}
	
	
	 /**
     * 获得<省,市, , ,>这种形式，由下往上
     */
	public Map<String,Object> getParentById(int id){
		
		Map<String,Object> parentInfo = regionDao.getParentById(id);
		return parentInfo;
		
	}
	
	
	/**
	 * 由父节点 递归查询下面所有子节点
	 *@描述
	 *@param pid
	 *@return
	 *@作者 ccchen
	 *@时间 2017年7月19日上午11:18:19
	 */
	public List<RegionEntity> getChildrenList(int pid){
		List<RegionEntity> regionEntities = regionDao.queryListByPid(pid);
		if(regionEntities!=null&&regionEntities.size()>0){
			for(RegionEntity region : regionEntities){
				region.setList(getChildrenList(region.getId()));
			}	
		}
		return regionEntities;
		
	}	
	
	public Map<String, Boolean> isExistEnterpriseAndUser(int pid,Map<String, Boolean> map){
		
		List<RegionEntity> regionEntities = regionDao.queryListByPid(pid);
		
		if(regionEntities!=null&&regionEntities.size()>0){
			for(RegionEntity region : regionEntities){
				
				//判断是否有企业
				int i = enterpriseinfoDao.isExistByRegion(region.getId());
				if(i>0){
					map.put("enterprise", true);
					return map;
				}
				
				//判断是否有用户
				i = sysUserDao.isExistByRegion(region.getId());
				if(i>0){
					map.put("user", true);
					return map;
				}
				
				//如果没有递归继续查询
				isExistEnterpriseAndUser(region.getId(),map);
				
			}	
		}
		
		return map;		
		
	}
	
	
}
