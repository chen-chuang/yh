package io.renren.modules.yh.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.dao.ConfigtableDao;
import io.renren.modules.yh.dao.IntegrationcashDao;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.entity.IntegrationcashEntity;
import io.renren.modules.yh.service.IntegrationcashService;



@Service("integrationcashService")
public class IntegrationcashServiceImpl implements IntegrationcashService {
	@Autowired
	private IntegrationcashDao integrationcashDao;
	
	@Autowired
	private ConfigtableDao configtableDao;
	
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
	
	@Override
	@Transactional
	public void apply(IntegrationcashEntity integrationcashEntity,SysUserEntity user){
		
		ConfigtableEntity configtableEntities = configtableDao.getConfig(user);
		
		if(configtableEntities!=null){
			
			String proportion = configtableEntities.getConfigValue();
			BigDecimal proportionNum = new BigDecimal(proportion);
			
			BigDecimal amount =  integrationcashEntity.getWithdrawalamount();
			
			//通过比例计算提及的积分
			Long useIntegration = amount.multiply(amount).longValue();
			integrationcashEntity.setIntegration(useIntegration);
			
			integrationcashDao.save(integrationcashEntity);
		}
		
		
	}
	
	@Override
	public Map<String, Object> getIntegrationInfo(SysUserEntity user){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//得到申请中的总积分
		Long applySum = integrationcashDao.getSumIntegration(user.getUserId());
		
		//换算比例 展示能兑换多少钱
        ConfigtableEntity configtableEntities = configtableDao.getConfig(user);
		
		if(configtableEntities!=null){
			
			String proportion = configtableEntities.getConfigValue();
			Integer proportionNum = Integer.valueOf(proportion);
			
			Long userIntegral =  user.getUserIntegral();
			
			Long ableCash = userIntegral/proportionNum;
			map.put("ableCash", ableCash);
		}
		
		map.put("sum", user.getUserIntegral());
		map.put("applySum",applySum);
		
		return map;
		
	}
}
