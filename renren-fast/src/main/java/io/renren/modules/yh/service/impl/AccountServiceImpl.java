package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.R;
import io.renren.modules.yh.dao.AccountDao;
import io.renren.modules.yh.dao.WithdrawDao;
import io.renren.modules.yh.entity.AccountEntity;
import io.renren.modules.yh.service.AccountService;



@Service("accountService")
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private WithdrawDao withdrawDao;
	
	@Override
	public AccountEntity queryObject(Integer id){
		return accountDao.queryObject(id);
	}
	
	@Override
	public List<AccountEntity> queryList(Map<String, Object> map){
		return accountDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return accountDao.queryTotal(map);
	}
	
	@Override
	public void save(AccountEntity account){
		accountDao.save(account);
	}
	
	@Override
	public void update(AccountEntity account){
		accountDao.update(account);
	}
	
	@Override
	public void delete(Integer id){
		accountDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		accountDao.deleteBatch(ids);
	}
	
	@Override
	public void updateByAgency(AccountEntity account){
		accountDao.updateByAgency(account);
	}
	
	@Override
	public void queryByAgency(Long agencyId){
		accountDao.queryByAgency(agencyId);
	}
	
	@Override
	public R detailInfo(){
		Map<String,Object> map =new HashMap<>();
		
		BigDecimal sum = accountDao.sum();
		
		BigDecimal ed =new BigDecimal(0);
		
		//BigDecimal ing = new BigDecimal(0);
		
		BigDecimal two = new BigDecimal(0);
		
		BigDecimal one = new BigDecimal(0);
		
		sum= sum==null?new BigDecimal(0):sum;
		
		List<Map<String,Object>> rmap = withdrawDao.groupSum();
		
		if(rmap!=null){
			for(Map<String,Object> r : rmap){
				
				if("1".equals(r.get("withdraw_status").toString())){
					one = new BigDecimal(r.get("withdrawalAmount").toString());
				}
				
				if("2".equals(r.get("withdraw_status").toString())){
					two= new BigDecimal(r.get("withdrawalAmount").toString());
				}
				
				if("3".equals(r.get("withdraw_status").toString())){
					ed = new BigDecimal(r.get("withdrawalAmount").toString());
				}
			}
			
			
		}
		map.put("sum", sum);
		map.put("ed", ed);
		map.put("ing", one.add(two));
		
		return R.ok(map);
	}
	
	
}
