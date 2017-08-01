package io.renren.modules.yh.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.api.entity.UserEntity;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.dao.ConfigtableDao;
import io.renren.modules.yh.dao.OrderintegrationDao;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.entity.OrderintegrationEntity;
import io.renren.modules.yh.service.OrderintegrationService;



@Service("orderintegrationService")
public class OrderintegrationServiceImpl implements OrderintegrationService {
	@Autowired
	private OrderintegrationDao orderintegrationDao;
	
	@Autowired
	private ConfigtableDao configtableDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public OrderintegrationEntity queryObject(Integer id){
		return orderintegrationDao.queryObject(id);
	}
	
	@Override
	public List<OrderintegrationEntity> queryList(Map<String, Object> map){
		return orderintegrationDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return orderintegrationDao.queryTotal(map);
	}
	
	@Override
	public void save(OrderintegrationEntity orderintegration){
		orderintegrationDao.save(orderintegration);
	}
	
	@Override
	public void update(OrderintegrationEntity orderintegration){
		orderintegrationDao.update(orderintegration);
	}
	
	@Override
	public void delete(Integer id){
		orderintegrationDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		orderintegrationDao.deleteBatch(ids);
	}

	@Override
	public void rebate(String startTime, String endTime, String deliveryUserId, String sumIntegration) {
		orderintegrationDao.rebate(startTime, endTime, deliveryUserId);		
		
		//减去总积分
		SysUserEntity user = sysUserDao.queryObject(deliveryUserId);
		Long remainderIntegration = user.getUserIntegral() - Long.valueOf(sumIntegration);
		sysUserDao.addIntegral(remainderIntegration, user.getUserId());
		
	}

	@Override
	public void rebateByIds(Integer[] ids, String sumIntegration,String deliveryUserId) {
		orderintegrationDao.rebateByIds(ids);
		
		//减去总积分
		SysUserEntity user = sysUserDao.queryObject(deliveryUserId);
		Long remainderIntegration = user.getUserIntegral() - Long.valueOf(sumIntegration);
		sysUserDao.addIntegral(remainderIntegration, user.getUserId());
	}

	@Override
	public Map<String, Object> rebateDetailByIds(Integer[] ids) {
		Map<String, Object> map = orderintegrationDao.rebateDetailByIds(ids);
		
		Long sum_integration = Long.parseLong(String.valueOf(map.get("sum_integration")));
		/*	map.get("sum_price")
			map.get("user_id")*/
		SysUserEntity user = sysUserDao.queryObject(map.get("user_id"));
		
		//换算比例 展示能兑换多少钱
        ConfigtableEntity configtableEntities = configtableDao.getConfigIntegerationCash(user);
		
		if(configtableEntities!=null){
			
			String proportion = configtableEntities.getConfigValue();
			Integer proportionNum = Integer.valueOf(proportion);
			
			Double ableCash = sum_integration/(double)proportionNum;
			map.put("ableCash", ableCash);
		}
			
		return map;
		
	}

	@Override
	public Map<String, Object> rebateDetail(String startTime, String endTime, String deliveryUserId) {
		Map<String, Object> map = orderintegrationDao.rebateDetail(startTime, endTime, deliveryUserId);
		Long sum_integration = Long.parseLong(String.valueOf(map.get("sum_integration")));
		SysUserEntity user = sysUserDao.queryObject(deliveryUserId);
		
		//换算比例 展示能兑换多少钱
        ConfigtableEntity configtableEntities = configtableDao.getConfigIntegerationCash(user);
		
		if(configtableEntities!=null){
			
			String proportion = configtableEntities.getConfigValue();
			Integer proportionNum = Integer.valueOf(proportion);
			
			Double ableCash = sum_integration/(double)proportionNum;
			map.put("ableCash", ableCash);
		}
		
		
		return map;
	}
	
}
