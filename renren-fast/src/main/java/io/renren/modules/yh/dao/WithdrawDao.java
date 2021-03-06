package io.renren.modules.yh.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.yh.entity.WithdrawEntity;

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

	BigDecimal getSumApplyCash(Long userId);

	List<Map<String, Object>> groupSum();
	
}
