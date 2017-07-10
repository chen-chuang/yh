package io.renren.modules.yh.service;

import io.renren.modules.yh.entity.WithdrawEntity;

import java.util.List;
import java.util.Map;

/**
 * 提现记录表（区域经销商）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public interface WithdrawService {
	
	WithdrawEntity queryObject(Integer id);
	
	List<WithdrawEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WithdrawEntity withdraw);
	
	void update(WithdrawEntity withdraw);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
