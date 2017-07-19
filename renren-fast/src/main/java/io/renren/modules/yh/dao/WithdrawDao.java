package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.WithdrawEntity;
import io.renren.modules.sys.dao.BaseDao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 提现记录表（区域经销商）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface WithdrawDao extends BaseDao<WithdrawEntity> {

	void operate(Map<String, Object> params);
	
}
