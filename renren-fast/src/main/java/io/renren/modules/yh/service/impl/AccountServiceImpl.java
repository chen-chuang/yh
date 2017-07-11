package io.renren.modules.yh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.modules.yh.dao.AccountDao;
import io.renren.modules.yh.entity.AccountEntity;
import io.renren.modules.yh.service.AccountService;



@Service("accountService")
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;
	
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
	
}
