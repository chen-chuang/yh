package io.renren.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.renren.modules.api.entity.dto.LoginDTO;
import io.renren.modules.api.service.TokenService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.yh.dao.ConfigtableDao;
import io.renren.modules.yh.dao.IntegrationcashDao;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.service.RegionService;



/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private IntegrationcashDao integrationcashDao;
	
	@Autowired
	private ConfigtableDao configtableDao;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}
	
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserDao.queryObject(userId);
	}

	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		sysUserDao.save(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		List<Long> roleIdList =new ArrayList<Long>();
		roleIdList.add(user.getUserPermission().longValue());
		sysUserRoleService.saveOrUpdate(user.getUserId(), roleIdList);
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		sysUserDao.update(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		sysUserDao.deleteBatch(userId);
	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		/*if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());*/
		
		//判断是否越权
		/*if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}*/
	}
	
	@Override
	public void setPermission(Long userId, int permissionId) {
		sysUserDao.setPermission(userId,permissionId);
		
	}
	
	public void setExpiryDate(Long userId, Date expiryDate){
		sysUserDao.setExpiryDate(userId, expiryDate);
	}

	@Override
	public void setRegion(Long userId, int regionId,String regionName) {
		 
		sysUserDao.setRegion(userId,regionId,regionName);
	}
	
	@Override
	public Object apiLogin(String phoneNumber, String password){		
		
		String msg = "";
		
		SysUserEntity user = sysUserDao.apiGetUserByPhone(phoneNumber);
		
		if(user == null){
			msg = "您输入的手机号不存在！";
			return msg;
		}
		
		password = new Sha256Hash(password,user.getSalt()).toHex();
		
		LoginDTO info = sysUserDao.apiValidateLogin(phoneNumber,password);
		
		if(info == null){
			msg = "您输入的密码有误！";
			return msg;
		}else{
			Map<String, Object> map = tokenService.createToken(Long.valueOf(info.getUserID()));
			info.setToken(map.get("token").toString());
		}		
		
		return info;		
		
	}
	
	@Override
	public List<Map<String, Object>> getDeliveryPerson(Long userId){
		
		List<Map<String, Object>> maps = sysUserDao.getDeliveryPerson(userId);
		return maps;	
		
	}
	
	@Override
	public List<Map<String, Object>> getDelivery(Long userId){
		List<Map<String, Object>> maps = sysUserDao.getDelivery(userId);
		return maps;	
	}
	
	@Override
	public int validateOnlyAgency(String areaId, Long userId){
		return sysUserDao.validateOnlyAgency(areaId,userId);
	}

	@Override
	public Map<String, Object> apiUserIntegral(SysUserEntity user) {
			
		Map<String,Object> map = new HashMap<String,Object>();
		
		//得到申请中的总积分
		Long applySum = integrationcashDao.getSumIntegration(user.getUserId());
		
		
		ConfigtableEntity configtableEntity = configtableDao.getConfigIntegerationCash(user);
		
		if(configtableEntity!=null){
			map.put("scoreScale", configtableEntity.getConfigValue());
		}else{
			map.put("scoreScale", 1);
		}
		
		//得到能使用的积分
		if(user.getUserIntegral()==null||user.getUserIntegral().equals(0)){
			map.put("userIntegral", 0);
		}else{
			map.put("userIntegral", user.getUserIntegral()-applySum);
		}		
		
		
		return map;
	}
	
	@Override
	public void addIntegral(Long integral,Long userId){
		sysUserDao.addIntegral(integral, userId);
	}
}
