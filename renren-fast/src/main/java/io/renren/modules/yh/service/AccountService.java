package io.renren.modules.yh.service;

import io.renren.modules.yh.entity.AccountEntity;

import java.util.List;
import java.util.Map;

/**
 * 资金账户表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-11 17:54:19
 */
public interface AccountService {
	
	AccountEntity queryObject(Integer id);
	
	List<AccountEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AccountEntity account);
	
	void update(AccountEntity account);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	void updateByAgency(AccountEntity account);
}
