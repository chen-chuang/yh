package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.Constant;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.yh.entity.enums.EnumPermission;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		/*if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}*/
		
		if(!getUser().getUserPermission().equals(EnumPermission.ADMIN.getType())){
			params.put("createUserId", getUserId());
		}
		
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");
		
		//sha256加密
		password = new Sha256Hash(password, getUser().getSalt()).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();
				
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateUserId(getUserId());
		
		if(getUser().getUserPermission().equals(EnumPermission.ADMIN.getType())){
			if(user.getUserPermission().equals(EnumPermission.AGENCY.getType())){
				int count = sysUserService.validateOnlyAgency(user.getAreaId(),null);
				if(count>0){
					return R.error("该区域已存在区域经销商，请重新设置区域！");
				}
			}
		}
		
		if(getUser().getUserPermission().equals(EnumPermission.AGENCY.getType())){
			user.setBelongToAgencyId(getUser().getUserId());
			user.setBelongToAgencyName(getUser().getUsername());
			
			//配送员和销售员行政区域  和  区域经销商一个级别
			if(user.getUserPermission().equals(EnumPermission.DELIVERY_F.getType())
					||user.getUserPermission().equals(EnumPermission.SALE.getType())){
				user.setAreaId(getUser().getAreaId());
				user.setUserArea(getUser().getUserArea());
			}
			
			//配送员 按 填的 写入
			if(user.getUserPermission().equals(EnumPermission.DELIVERY_P.getType())){
				user.setAreaId(user.getAreaId());
				user.setUserArea(user.getUserArea());
			}
			
		}
		
		sysUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);
		
		user.setCreateUserId(getUserId());
		
		if(getUser().getUserPermission().equals(EnumPermission.ADMIN.getType())){
			if(user.getUserPermission().equals(EnumPermission.AGENCY.getType())){
				int count = sysUserService.validateOnlyAgency(user.getAreaId(),user.getUserId());
				if(count>0){
					return R.error("该区域已存在区域经销商，请重新设置区域！");
				}
			}
		}
		
		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
	
	
	@RequestMapping("/setPermission")
	public R setPermission(@RequestParam("userId") Long userId,
			@RequestParam("permissionId") int permissionId){
		
	   /* SysUserEntity currentUser = this.getUser();
	    
	    if(currentUser.getUserPermission().equals(EnumPermission.AGENCY)){
	    	
	    }*/
		
		sysUserService.setPermission(userId,permissionId);
		return R.ok();
	}
	
	@RequestMapping("/setExpiryDate")
	public R setExpiryDate(@RequestParam("userId") Long userId,
			@RequestParam("expiryDate") String expiryDate){
		
		Date newDate = DateUtils.parse(expiryDate, DateUtils.DATE_PATTERN);
		System.out.println(expiryDate);		
		sysUserService.setExpiryDate(userId,newDate);
		
		return R.ok();
	}
	
	@RequestMapping("/setRegion")
	public R setRegion(@RequestParam("userId") Long userId,
			@RequestParam("regionId") int regionId,
			@RequestParam("regionName") String regionName){		
		
		
		if(getUser().getUserPermission().equals(EnumPermission.ADMIN.getType())){
				int count = sysUserService.validateOnlyAgency(String.valueOf(regionId),userId);
				return R.error("该区域已存在区域经销商，请重新设置！");
		}
		
		sysUserService.setRegion(userId,regionId,regionName);
		return R.ok();
	}
	
	
	@RequestMapping("/currentLoginUser")
	public R currentLoginUser(){
		
		SysUserEntity currentLoginUser = this.getUser();
		
		return R.ok().put("currentLoginUser", currentLoginUser);
	}
	
	@RequestMapping("/getDelivery")
	public R getDelivery(){
		List<Map<String, Object>> userinfo = sysUserService.getDelivery(getUserId());
		return R.ok().put("userinfo", userinfo);
	}
	
	
}
