package io.renren.modules.sys.service;
import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.WithdrawEntity;

/**
 * 提现记录表（区域经销商）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
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
