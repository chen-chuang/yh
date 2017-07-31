package io.renren.modules.sys.service;

import io.renren.modules.api.entity.dto.LoginDTO;
import io.renren.modules.sys.entity.SysUserEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService {

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
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	SysUserEntity queryObject(Long userId);
	
	/**
	 * 查询用户列表
	 */
	List<SysUserEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	void save(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);
	
	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	int updatePassword(Long userId, String password, String newPassword);

	void setPermission(Long userId, int permissionId);

	void setExpiryDate(Long userId, Date expiryDate);

	void setRegion(Long userId, int regionId, String regionName);

	Object apiLogin(String phoneNumber, String password);

	/**
	 * 根据发货员ID得到所有配送员(通过所属经销商)
	 *@描述
	 *@param userId
	 *@return
	 *@作者 ccchen
	 *@时间 2017年7月26日下午4:29:42
	 */
	List<Map<String, Object>> getDeliveryPerson(Long userId);

	List<Map<String, Object>> getDelivery(Long userId);
}
