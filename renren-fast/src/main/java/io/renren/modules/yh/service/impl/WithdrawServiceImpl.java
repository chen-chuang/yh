package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.dao.AccountDao;
import io.renren.modules.yh.dao.WithdrawDao;
import io.renren.modules.yh.entity.AccountEntity;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.entity.WithdrawEntity;
import io.renren.modules.yh.entity.enums.EnumWithDrawStatus;
import io.renren.modules.yh.service.WithdrawService;



@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService {
	@Autowired
	private WithdrawDao withdrawDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public WithdrawEntity queryObject(Integer id){
		return withdrawDao.queryObject(id);
	}
	
	@Override
	public List<WithdrawEntity> queryList(Map<String, Object> map){
		return withdrawDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return withdrawDao.queryTotal(map);
	}
	
	@Override
	public void save(WithdrawEntity withdraw){
		withdrawDao.save(withdraw);
	}
	
	@Override
	public void update(WithdrawEntity withdraw){
		withdrawDao.update(withdraw);
	}
	
	@Override
	public void delete(Integer id){
		withdrawDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		withdrawDao.deleteBatch(ids);
	}
	
	@Override
	public void apply(WithdrawEntity withdrawEntity){
		withdrawDao.save(withdrawEntity);
	}
	
	@Override
	@Transactional
	public void operate(Map<String, Object> params){
		
		withdrawDao.operate(params);
		
		//如果完成，减去相应资金
		if(params.get("withdrawStatus").equals(EnumWithDrawStatus.COMPLETE.getStatus())){
			WithdrawEntity withdrawEntity = queryObject(Integer.valueOf(params.get("id").toString()));
			
			AccountEntity accountEntity = accountDao.queryByUserId(withdrawEntity.getApplyUserId());
			
			BigDecimal surplusPrice = accountEntity.getPrice().subtract(withdrawEntity.getWithdrawalamount());			
			
			accountDao.updatePrice(withdrawEntity.getApplyUserId(),surplusPrice);
		}
	}

	@Override
	public Map<String, Object> getCashInfo(SysUserEntity user) {
		
	    Map<String,Object> map = new HashMap<String,Object>();
		
		//得到申请中的总金额
		BigDecimal applySum = withdrawDao.getSumApplyCash(user.getUserId());
		
		applySum = applySum ==null?new BigDecimal(0):applySum;
		
		AccountEntity accountEntity = accountDao.queryByUserId(user.getUserId());
		
		if(accountEntity!=null){
			BigDecimal ableCash = accountEntity.getPrice().subtract(applySum);
			map.put("ableCash", ableCash);
			if(ableCash.compareTo(new BigDecimal(0))==-1){
				map.put("ableCash", 0);
			}
		}
		
		if(accountEntity!=null){
			map.put("sum", accountEntity.getPrice());
		}
		
		map.put("applySum",applySum);
		
		return map;
	}
	
}
