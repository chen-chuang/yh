package io.renren.modules.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.sys.dao.CcountDao;
import io.renren.modules.sys.entity.CcountEntity;
import io.renren.modules.sys.service.CcountService;



@Service("ccountService")
public class CcountServiceImpl implements CcountService {
	@Autowired
	private CcountDao ccountDao;
	
	@Override
	public CcountEntity queryObject(Integer id){
		return ccountDao.queryObject(id);
	}
	
	@Override
	public List<CcountEntity> queryList(Map<String, Object> map){
		return ccountDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ccountDao.queryTotal(map);
	}
	
	@Override
	public void save(CcountEntity ccount){
		ccountDao.save(ccount);
	}
	
	@Override
	public void update(CcountEntity ccount){
		ccountDao.update(ccount);
	}
	
	@Override
	public void delete(Integer id){
		ccountDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ccountDao.deleteBatch(ids);
	}
	
}
