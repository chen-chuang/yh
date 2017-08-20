package io.renren.modules.yh.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.WithdrawEntity;
import io.renren.modules.yh.entity.enums.EnumPermission;
import io.renren.modules.yh.entity.enums.EnumWithDrawStatus;
import io.renren.modules.yh.service.WithdrawService;




/**
 * 提现记录表（区域经销商）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("withdraw")
public class WithdrawController extends AbstractController {
	@Autowired
	private WithdrawService withdrawService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("withdraw:list")
	public R list(@RequestParam Map<String, Object> params){
		
		SysUserEntity user = this.getUser();
		if(user.getUserPermission().equals(EnumPermission.AGENCY.getType())){
			params.put("applyUserId", user.getUserId());
		}
		
		if(user.getUserPermission().equals(EnumPermission.ADMIN.getType())){
			params.put("userId", user.getUserId());
		}		
		
		//查询列表数据
        Query query = new Query(params);

		List<WithdrawEntity> withdrawList = withdrawService.queryList(query);
		int total = withdrawService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(withdrawList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("withdraw:info")
	public R info(@PathVariable("id") Integer id){
		WithdrawEntity withdraw = withdrawService.queryObject(id);
		
		return R.ok().put("withdraw", withdraw);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("withdraw:save")
	public R save(@RequestBody WithdrawEntity withdraw){
		withdrawService.save(withdraw);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("withdraw:update")
	public R update(@RequestBody WithdrawEntity withdraw){
		withdrawService.update(withdraw);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("withdraw:delete")
	public R delete(@RequestBody Integer[] ids){
		withdrawService.deleteBatch(ids);
		
		return R.ok();
	}
	
	@RequestMapping("/apply")
	@RequiresPermissions("withdraw:apply")
	public R apply(@RequestParam("accountNum") String accountNum){
		
		BigDecimal num = new BigDecimal(accountNum);
		
		WithdrawEntity withdrawEntity = new WithdrawEntity();
		
		withdrawEntity.setWithdrawalamount(num);
		withdrawEntity.setApplyTime(new Date());
		withdrawEntity.setApplyUserId(getUserId());
		withdrawEntity.setWithdrawStatus(EnumWithDrawStatus.APPLY.getStatus());
		
		withdrawService.apply(withdrawEntity);
		
		return R.ok();
	}
	
	@RequestMapping("/operate")
	@RequiresPermissions("withdraw:operate")
	public R operate(@RequestParam("id") Integer id,
			@RequestParam("type") String type){
		Map<String, Object> params =new HashMap<String,Object>();	
		
		if(type.equals("accept")){
			params.put("withdrawStatus", EnumWithDrawStatus.ACCEPTED.getStatus());
		}
		
		if(type.equals("complete")){
			params.put("withdrawStatus", EnumWithDrawStatus.COMPLETE.getStatus());
		}
		
		params.put("id", id);
		
		params.put("userId", this.getUserId());
		
		params.put("operateTime", new Date());
		
		withdrawService.operate(params);
		
		return R.ok();
	}
	
	
	@RequestMapping("/getCashInfo")
	public R getCashInfo(){
		
		SysUserEntity user = this.getUser();
		
		Map<String,Object> info = withdrawService.getCashInfo(user);
		
		return R.ok().put("info", info);
	}
	
}
