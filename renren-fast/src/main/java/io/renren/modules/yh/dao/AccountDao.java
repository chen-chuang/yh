package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.AccountEntity;
import io.renren.modules.sys.dao.BaseDao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资金账户表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-11 17:54:19
 */
@Mapper
public interface AccountDao extends BaseDao<AccountEntity> {

	AccountEntity queryByUserId(Long applyUserId);

	void updatePrice(@Param("applyUserId")Long applyUserId, @Param("surplusPrice")BigDecimal surplusPrice);
	
}
