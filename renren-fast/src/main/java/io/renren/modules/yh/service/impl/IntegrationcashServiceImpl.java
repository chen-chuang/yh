package io.renren.modules.yh.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import io.renren.common.utils.R;
import io.renren.modules.api.entity.dto.WithDrawDTO;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.dao.ConfigtableDao;
import io.renren.modules.yh.dao.IntegrationcashDao;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.entity.IntegrationcashEntity;
import io.renren.modules.yh.entity.enums.EnumIntegrationCash;
import io.renren.modules.yh.service.IntegrationcashService;



@Service("integrationcashService")
public class IntegrationcashServiceImpl implements IntegrationcashService {
	@Autowired
	private IntegrationcashDao integrationcashDao;
	
	@Autowired
	private ConfigtableDao configtableDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	
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
		
		//申请前先检查申请中的积分，以防超
		//得到申请中的总积分
		Long applySum = integrationcashDao.getSumIntegration(user.getUserId());
		
		//换算比例 展示能兑换多少钱
        ConfigtableEntity configtableEntities = configtableDao.getConfigIntegerationCash(user);
		
		if(configtableEntities!=null){
			
			String proportion = configtableEntities.getConfigValue();
			BigDecimal proportionNum = new BigDecimal(proportion);
			
			//还可兑现积分
			Long userIntegral =  user.getUserIntegral()-applySum;
			
			BigDecimal amount =  integrationcashEntity.getWithdrawalamount();
			
			//通过比例计算提及的积分
			Long useIntegration = amount.multiply(proportionNum).longValue();
			integrationcashEntity.setIntegration(useIntegration);
			
			//用户剩余积分 大于 可提现积分
			if(userIntegral>integrationcashEntity.getIntegration()){
				
				integrationcashDao.save(integrationcashEntity);
			}
			
		}
		
		
	}
	
	@Override
	public Map<String, Object> getIntegrationInfo(SysUserEntity user){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//得到申请中的总积分
		Long applySum = integrationcashDao.getSumIntegration(user.getUserId());
		
		//换算比例 展示能兑换多少钱
        ConfigtableEntity configtableEntities = configtableDao.getConfigIntegerationCash(user);
		
		if(configtableEntities!=null){
			
			String proportion = configtableEntities.getConfigValue();
			Integer proportionNum = Integer.valueOf(proportion);
			
			//还可兑现积分
			Long userIntegral =  user.getUserIntegral()-applySum;
			
			Double ableCash = userIntegral/(double)proportionNum;
			map.put("ableCash", ableCash);
		}
		
		map.put("sum", user.getUserIntegral());
		map.put("applySum",applySum);
		
		return map;
		
	}
	
	@Override
	public R apiWithdraw(IntegrationcashEntity integrationcashEntity, SysUserEntity user){		
		
        ConfigtableEntity configtableEntities = configtableDao.getConfigIntegerationCash(user);
		
		if(configtableEntities!=null){
			
			String proportion = configtableEntities.getConfigValue();
			BigDecimal proportionNum = new BigDecimal(proportion);
			
			BigDecimal amount =  integrationcashEntity.getWithdrawalamount();
			
			//通过比例计算提及的积分
			Long useIntegration = amount.multiply(proportionNum).longValue();
			integrationcashEntity.setIntegration(useIntegration);
			
			integrationcashDao.save(integrationcashEntity);
			
			return R.ok();
		}else{
			//return R.error("所属区域经销商尚未设置积分兑现比例，暂不能兑现！");
			String proportion = "1";
			BigDecimal proportionNum = new BigDecimal(proportion);
			
			BigDecimal amount =  integrationcashEntity.getWithdrawalamount();
			
			//通过比例计算提及的积分
			Long useIntegration = amount.multiply(proportionNum).longValue();
			integrationcashEntity.setIntegration(useIntegration);
			
			integrationcashDao.save(integrationcashEntity);
			
			return R.ok();
		}
	}
	
	@Override
	public List<WithDrawDTO> apiWithdrawRecordList(Map<String, Object> map){
		
		List<WithDrawDTO> withDraw = integrationcashDao.apiWithdrawRecordList(map);
		
		return withDraw;
	}

	@Override
	@Transactional
	public void complete(String id) {
		
        IntegrationcashEntity integrationcashEntity = integrationcashDao.queryObject(Integer.valueOf(id));
		
		integrationcashEntity.setOperateTime(new Date());
		integrationcashEntity.setWithdrawStatus(EnumIntegrationCash.COMPLETE.getStatus());
		
		integrationcashDao.update(integrationcashEntity);			
		
		SysUserEntity userEntity = sysUserDao.queryObject(integrationcashEntity.getApplyUserId());
		Long remainderIntegration = userEntity.getUserIntegral() - integrationcashEntity.getIntegration();
		
		//公用一个方法 懒得改了   更新积分
		sysUserDao.addIntegral(remainderIntegration, userEntity.getUserId());
		
	}

	
}
