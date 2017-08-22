package io.renren.modules.yh.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.renren.common.utils.R;
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
	@Transactional
	public void rebate(Date dStartTime, Date dEndTime, String deliveryUserId, String sumIntegration) {
		orderintegrationDao.rebate(dStartTime, dEndTime, deliveryUserId);		
		
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
	public R rebateDetailByIds(Integer[] ids) {
		Map<String, Object> map = orderintegrationDao.rebateDetailByIds(ids);
		
		if(map==null){
			return R.error("暂无未返点记录！");
		}
		
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
		}else{
			return R.error("请先配置返点比例后在进行返点！");
		}
			
		return R.ok(map);
		
	}

	@Override
	public R rebateDetail(Date startTime, Date endTime, String deliveryUserId) {		
	
		Map<String, Object> map = orderintegrationDao.rebateDetail(startTime, endTime, deliveryUserId);
		
		if(map==null){
			return R.error("暂无未返点记录！");
		}
		
		Long sum_integration = Long.parseLong(String.valueOf(map.get("sum_integration")));
		SysUserEntity user = sysUserDao.queryObject(deliveryUserId);
		
		//换算比例 展示能兑换多少钱
        ConfigtableEntity configtableEntities = configtableDao.getConfigIntegerationCash(user);
		
		if(configtableEntities!=null){
			
			String proportion = configtableEntities.getConfigValue();
			Integer proportionNum = Integer.valueOf(proportion);
			
			Double ableCash = sum_integration/(double)proportionNum;
			map.put("ableCash", ableCash);
		}else{
			return R.error("请先配置返点比例后在进行返点！");
		}
		
		
		return R.ok(map);
	}
	
}
