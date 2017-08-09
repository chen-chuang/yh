package io.renren.modules.api.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.AppValidateUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.LoginDTO;
import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.api.entity.dto.WithDrawDTO;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.entity.IntegrationcashEntity;
import io.renren.modules.yh.entity.enums.EnumIntegrationCash;
import io.renren.modules.yh.service.CollectionService;
import io.renren.modules.yh.service.ConfigtableService;
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
	
	@Autowired
	private ConfigtableService configtableService;
	
	@AuthIgnore
	@RequestMapping("withdraw")
	public R withdraw(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			String userID = map.get("userID");
			String withdrawalAmount =  map.get("withdrawalAmount");
			
		    BigDecimal account = new BigDecimal(withdrawalAmount);
			
			SysUserEntity user =  sysUserService.queryObject(Long.valueOf(userID));
			
			IntegrationcashEntity integrationcashEntity = new IntegrationcashEntity();
			integrationcashEntity.setApplyTime(new Date());
			integrationcashEntity.setApplyUserId(user.getUserId());
			integrationcashEntity.setApplyUserName(user.getUsername());
			integrationcashEntity.setWithdrawalamount(account);
			integrationcashEntity.setWithdrawStatus(EnumIntegrationCash.APPLY.getStatus());		
			
			try{
				R r = integrationcashService.apiWithdraw(integrationcashEntity,user);
				return r;
			}catch (Exception e) {
				return R.error().put("info", "系统错误，请稍后再试...");
			}
		}else{
			return R.error();
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
	public R withdrawRecordList(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			String userID = map.get("userID");
			String page = map.get("pageIndex");
			String limit = map.get("pageSize");
			

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userID", userID);
			params.put("limit", limit);
			params.put("page", page);
			
			Query query = new Query(params);

			List<WithDrawDTO> withDrawList = integrationcashService.apiWithdrawRecordList(query);		
			
			return R.ok().put("info", withDrawList);	
		}else{
			return R.error();
		}				
		
	}
	
	
	/**
	 * 修改登录用户密码
	 */
	@AuthIgnore
	@RequestMapping("/changePassword")
	public R password(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			
			String newPassword = map.get("newPassword");
			String originalPassword = map.get("originalPassword");
			String userID=map.get("userID");
			
			if(StringUtils.isBlank(newPassword)){
				return R.error("新密码不能为空！");
			}
			
			if(originalPassword.equals(newPassword)){
				return R.error("新密码和原密码不能一样哦！");
			}
			
			SysUserEntity sysUserEntity = sysUserService.queryObject(Long.valueOf(map.get("userID")));
			
			//sha256加密
			originalPassword = new Sha256Hash(originalPassword, sysUserEntity.getSalt()).toHex();
			//sha256加密
			newPassword = new Sha256Hash(newPassword,sysUserEntity.getSalt()).toHex();
					
			//更新密码
			int count = sysUserService.updatePassword(Long.valueOf(userID), originalPassword, newPassword);
			if(count == 0){
				return R.error("您输入的原密码不正确！");
			}
			
			return R.ok();
		}else{
			return R.error();
		}
		
		
	}
	
	@AuthIgnore
	@RequestMapping("/login")
	public R login(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			Object info = sysUserService.apiLogin(map.get("phoneNumber"),map.get("password"));
			
			if(info instanceof LoginDTO){
				return R.ok().put("info", info);
			}else{
				return R.error(String.valueOf(info));
			}
		}else{
			return R.error();
		}
		
	}
	
	@AuthIgnore
	@RequestMapping("/myCollectionList")
	public R myCollectionList(@RequestParam Map<String, String> map){				
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userID", map.get("userID"));
			params.put("limit", map.get("pageSize"));
			params.put("page", map.get("pageIndex"));
			
			Query query = new Query(params);
			
			List<CollectionDTO> collectionList = collectionService.apiQueryCollectionList(query);
			
			return R.ok().put("info", collectionList);
		}else{
			return R.error();
		}		
		
	}
	
	@AuthIgnore
	@RequestMapping("/userIntegral")
	public R userIntegral(@RequestParam Map<String, String> mapRequest){	
		
		String sign = mapRequest.get("sign");
		mapRequest.remove("sign");
		
        String websign = AppValidateUtils.getSign(mapRequest);
		
		if(websign.equals(sign)){
			Map<String,Object> map = new HashMap<String,Object>();
			
			Long userId = Long.parseLong(mapRequest.get("userID"));
			SysUserEntity user = sysUserService.queryObject(userId);
			
			ConfigtableEntity configtableEntity = configtableService.getConfigIntegerationCash(user);
			
			map.put("userIntegral", user.getUserIntegral());
			
			if(configtableEntity!=null){
				map.put("scoreScale", configtableEntity.getConfigValue());
			}else{
				map.put("scoreScale", 1);
			}
			
			
			return R.ok().put("info", map);
		}else{
			return R.error();
		}
		
		
		
	}
	

}
