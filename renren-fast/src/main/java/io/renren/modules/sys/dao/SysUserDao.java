package io.renren.modules.sys.dao;

import io.renren.modules.api.entity.dto.LoginDTO;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.RegionEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);

	void setPermission(@Param("userId") Long userId,@Param("permissionId") int permissionId);

	void setExpiryDate(@Param("userId") Long userId,@Param("expiryDate")  Date expiryDate);

	void setRegion(@Param("userId") Long userId, @Param("regionId")int regionId, @Param("regionName")String regionName);

	int updateRegion(RegionEntity region);

	int isExistByRegion(Integer id);

	SysUserEntity apiGetUserByPhone(String phoneNumber);

	LoginDTO apiValidateLogin(@Param("phoneNumber")String phoneNumber, @Param("password")String password);
	
	void addIntegral(@Param("integral") Long integral,@Param("userId") Long userId);

	List<Map<String, Object>> getDeliveryPerson(Long userId);

	List<Map<String, Object>> getDelivery(Long userId);

	int validateOnlyAgency(String areaId);
}
