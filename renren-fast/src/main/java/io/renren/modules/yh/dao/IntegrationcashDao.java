package io.renren.modules.yh.dao;

import io.renren.modules.yh.entity.IntegrationcashEntity;
import io.renren.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分兑现表（销售人员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface IntegrationcashDao extends BaseDao<IntegrationcashEntity> {

	Long getSumIntegration(Long userId);
	
}
