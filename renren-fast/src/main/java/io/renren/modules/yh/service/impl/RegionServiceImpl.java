package io.renren.modules.yh.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.FaultAction;
import io.renren.modules.api.entity.dto.RegionDTO;
import io.renren.modules.api.entity.dto.TownDTO;
import io.renren.modules.sys.dao.SysUserDao;
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
	
	@Override
	public List<TownDTO> apiTown(String areaID){
		return regionDao.apiTown(areaID);
	}
	
	@Override
	public Map<String, Object> getParentKeyValue(String id){
		return regionDao.getParentKeyValue(id);
	}
	
	@Override
	public int onlyId(Integer id){
		return regionDao.onlyId(id);
	}
	
	@Override
	public List<RegionDTO> apiEnterpriseCity(){
		List<Map<String, String>> list = enterpriseinfoDao.apiEnterpriseCity(2);
		
		Map<String,String> existMap = new HashMap<String,String>();
		
		List<RegionDTO> trees = new ArrayList<>();
		
		List<RegionDTO> rootTrees = new ArrayList<RegionDTO>();
		
		//根据底层节点找到递归找到父节点，组成list
		for(Map<String, String> map : list){
            int id = Integer.parseInt(map.get("areaId"));
            List<String> areaIds = getFullRegion(id);
            for(int i=0;i<areaIds.size();i++){
            	if(!existMap.containsKey(areaIds.get(i))){
            		RegionDTO regionEntity =  regionDao.queryTreeObject(areaIds.get(i));
            		trees.add(regionEntity);
            	}
            	existMap.put(areaIds.get(i), areaIds.get(i));
            }
            
		}
		
		//拼成树
		
        for (RegionDTO tree : trees) {
            if(tree.getPid() == 0){
                rootTrees.add(tree);
            }
            for (RegionDTO t : trees) {
                if(t.getPid() == tree.getId()){
                    if(tree.getSub() == null){
                        List<RegionDTO> myChildrens = new ArrayList<RegionDTO>();
                        myChildrens.add(t);
                        tree.setSub(myChildrens);
                    }else{
                        tree.getSub().add(t);
                    }
                }
            }
        }
		
		return rootTrees;
	}

	@Override
	public List<RegionDTO> apiApiFactoryCity(){
        List<Map<String, String>> list = enterpriseinfoDao.apiEnterpriseCity(1);
		
		Map<String,String> existMap = new HashMap<String,String>();
		
		List<RegionDTO> trees = new ArrayList<>();
		
		//根据底层节点找到递归找到父节点，组成list
		for(Map<String, String> map : list){
            int id = Integer.parseInt(map.get("areaId"));
            List<String> areaIds = getFullRegion(id);
            for(int i=0;i<areaIds.size();i++){
            	if(!existMap.containsKey(areaIds.get(i))){
            		RegionDTO regionEntity =  regionDao.queryTreeObject(areaIds.get(i));
            		trees.add(regionEntity);
            	}
            	existMap.put(areaIds.get(i), areaIds.get(i));
            }
            
		}
		
		//拼成树
		  List<RegionDTO> rootTrees = new ArrayList<RegionDTO>();
	        for (RegionDTO tree : trees) {
	            if(tree.getPid() == 0){
	                rootTrees.add(tree);
	            }
	            for (RegionDTO t : trees) {
	                if(t.getPid() == tree.getId()){
	                    if(tree.getSub() == null){
	                        List<RegionDTO> myChildrens = new ArrayList<RegionDTO>();
	                        myChildrens.add(t);
	                        tree.setSub(myChildrens);
	                    }else{
	                        tree.getSub().add(t);
	                    }
	                }
	            }
	        }
		
		return rootTrees;
	}
	
}
