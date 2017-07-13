package io.renren.modules.yh.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.api.entity.dto.EnterpriseDeatailInfoDTO;
import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.yh.entity.EnterpriseinfoEntity;

/**
 * 企业信息表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@Mapper
public interface EnterpriseinfoDao extends BaseDao<EnterpriseinfoEntity> {

	List<EnterpriseinfoEntity> apiQueryList(Map<String, Object> map);
	
	EnterpriseDeatailInfoDTO apiEnterpriseByID(String enterpriseId); 
	
}
