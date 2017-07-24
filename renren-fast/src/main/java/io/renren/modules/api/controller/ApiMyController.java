package io.renren.modules.api.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.api.entity.dto.WithDrawDTO;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.yh.entity.IntegrationcashEntity;
import io.renren.modules.yh.entity.enums.EnumIntegrationCash;
import io.renren.modules.yh.service.CollectionService;
import io.renren.modules.yh.service.IntegrationcashService;
import io.renren.modules.yh.service.OrderService;

@RequestMapping("/api")
@RestController
public class ApiMyController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private IntegrationcashService integrationcashService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private CollectionService collectionService;
	
	@AuthIgnore
	@RequestMapping("withdraw")
	public R withdraw(HttpServletRequest request){
		
		String userID = request.getParameter("userID");
		String withdrawalAmount = request.getParameter("withdrawalAmount");
		
	    BigDecimal account = new BigDecimal(withdrawalAmount);
		
		SysUserEntity user =  sysUserService.queryObject(Long.valueOf(userID));
		
		IntegrationcashEntity integrationcashEntity = new IntegrationcashEntity();
		integrationcashEntity.setApplyTime(new Date());
		integrationcashEntity.setApplyUserId(user.getUserId());
		integrationcashEntity.setApplyUserName(user.getUsername());
		integrationcashEntity.setWithdrawalamount(account);
		integrationcashEntity.setWithdrawStatus(EnumIntegrationCash.APPLY.getStatus());		
		
		try{
			integrationcashService.apiWithdraw(integrationcashEntity,user);
			return R.ok().put("info", "申请成功");
		}catch (Exception e) {
			return R.ok().put("info", "申请失败");
		}
		
		
	}
	
	@AuthIgnore
	@RequestMapping("queryOrder")
	public R queryOrder(HttpServletRequest request){
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String townID = request.getParameter("townID");
		
		List<OrderDetailInfo> orderDetailInfo = orderService.apiQueryOrder(startTime,endTime,townID);
		
		return R.ok().put("info", orderDetailInfo);
	}
	
	@AuthIgnore
	@RequestMapping("withdrawRecordList")
	public R withdrawRecordList(HttpServletRequest request){
		
		String userID = request.getParameter("userID");
		String page = request.getParameter("pageIndex");
		String limit = request.getParameter("pageSize");
		

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("limit", limit);
		params.put("page", page);
		
		Query query = new Query(params);

		List<WithDrawDTO> withDrawList = integrationcashService.apiWithdrawRecordList(query);
		
		
		return R.ok().put("info", withDrawList);	
		
		
	}
	
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@AuthIgnore
	@RequestMapping("/changePassword")
	public R password(String userID,String originalPassword, String newPassword){
		
		//Assert.isBlank(newPassword, "新密码不为能空");
		
		SysUserEntity sysUserEntity = sysUserService.queryObject(Long.valueOf(userID));
		
		//sha256加密
		originalPassword = new Sha256Hash(originalPassword, sysUserEntity.getSalt()).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword,sysUserEntity.getSalt()).toHex();
				
		//更新密码
		int count = sysUserService.updatePassword(Long.valueOf(userID), originalPassword, newPassword);
		if(count == 0){
			return R.ok().put("info", "原密码不正确");
		}
		
		return R.ok().put("info", null);
	}
	
	@AuthIgnore
	@RequestMapping("/login")
	public R login(String phoneNumber,String password){		
		
		Map<String, Object> info = sysUserService.apiLogin(phoneNumber,password);
		
		return R.ok().put("info", info);
	}
	
	@AuthIgnore
	@RequestMapping("/myCollectionList")
	public R login(String userID,String pageIndex,String pageSize){		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("limit", pageSize);
		params.put("page", pageIndex);
		
		Query query = new Query(params);
		
		List<CollectionDTO> collectionList = collectionService.apiQueryCollectionList(query);
		
		return R.ok().put("info", collectionList);
		
	}
	
	@AuthIgnore
	@RequestMapping("/userIntegral")
	public R userIntegral(String userID){		
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Long userId = Long.parseLong(userID);
		SysUserEntity user = sysUserService.queryObject(userId);
		
		map.put("userIntegral", user.getUserIntegral());
		
		return R.ok().put("info", map);
		
	}
	

}
