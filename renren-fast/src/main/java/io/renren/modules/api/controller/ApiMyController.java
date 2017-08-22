package io.renren.modules.api.controller;

import java.io.File;
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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.AppValidateUtils;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.FileUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.utils.alipay.RandomUtil;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.CollectionDTO;
import io.renren.modules.api.entity.dto.LoginDTO;
import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.api.entity.dto.VersionDTO;
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
	public R queryOrder(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			String startTime = map.get("startTime");
			String endTime = map.get("endTime");
			String townID = map.get("townID");
			
			List<OrderDetailInfo> orderDetailInfo = orderService.apiQueryOrder(startTime,endTime,townID);
			
			return R.ok().put("info", orderDetailInfo);
		}else{
			return R.error();
		}
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
			
		/*	//sha256加密
			originalPassword = new Sha256Hash(originalPassword, sysUserEntity.getSalt()).toHex();
			//sha256加密
			newPassword = new Sha256Hash(newPassword,sysUserEntity.getSalt()).toHex();*/
					
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
			
			map = sysUserService.apiUserIntegral(user);	
			
			return R.ok().put("info", map);
		}else{
			return R.error();
		}
		
	}
	
	@AuthIgnore
	@RequestMapping("/verifyVerificationcode")
	public R verifyVerificationcode(@RequestParam Map<String, String> mapRequest){	
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		int count = sysUserService.validatePhone(mapRequest.get("userPhoneNumber"));
		if(count<1){
			return R.error("系统中不存在该手机号！");
		}
		
		String sign = mapRequest.get("sign");
		mapRequest.remove("sign");
		
        String websign = AppValidateUtils.getSign(mapRequest);
		
		if(websign.equals(sign)){
			
			int number = RandomUtil.getRandNum(1000, 9000);
			//初始化client,apikey作为所有请求的默认值(可以为空)
			YunpianClient clnt = new YunpianClient(ConfigConstant.YUPIAN_SMS_APIKEY).init();

			//修改账户信息API
			Map<String, String> param = clnt.newParam(2);
			param.put(YunpianClient.MOBILE, mapRequest.get("userPhoneNumber"));
			param.put(YunpianClient.TEXT, "【恒通烟花易购】您正在找回密码，短信验证码为"+number);
			Result<SmsSingleSend> r = clnt.sms().single_send(param);
			clnt.close(); 
			if(r.getCode().equals(0)){
				map.put("verificationcode", number);
				return R.ok().put("info", map);
			}else{
				return R.error(r.getMsg());
			}
			
		}else{
			return R.error();
		}
		
	}
	
	@AuthIgnore
	@RequestMapping("/forgetPassword")
	public R forgetPassword(@RequestParam Map<String, String> mapRequest){	
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sign = mapRequest.get("sign");
		mapRequest.remove("sign");
		
        String websign = AppValidateUtils.getSign(mapRequest);
		
		if(websign.equals(sign)){
			sysUserService.apiForgetPassword(mapRequest.get("newPassword"),mapRequest.get("userPhoneNumber"));
			return R.ok();
			
		}else{
			return R.error();
		}
		
	}
	
	@AuthIgnore
	@RequestMapping("/getversion")
	public R getversion(@RequestParam Map<String, String> mapRequest){	
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sign = mapRequest.get("sign");
		mapRequest.remove("sign");
		
        String websign = AppValidateUtils.getSign(mapRequest);
		
		if(websign.equals(sign)){
			String version  = "";
			
			if(mapRequest.get("systemType").equals("iOS")){
				version = FileUtils.readFileByLines(ConfigConstant.VERSION_DIR+File.separator+"ios_version.txt");
				
			}
			
			if(mapRequest.get("systemType").equals("Android")){
				version = FileUtils.readFileByLines(ConfigConstant.VERSION_DIR+File.separator+"android_version.txt");
			}
			
			List<VersionDTO> versionDTOs = new Gson().fromJson(version,new TypeToken<List<VersionDTO>>() {}.getType() );
			
			if(versionDTOs!=null){
				return R.ok().put("info", versionDTOs.get(0));
			}else{
				return R.ok();
			}
			
			
		}else{
			return R.error();
		}
		
	}
	
	
	

}
