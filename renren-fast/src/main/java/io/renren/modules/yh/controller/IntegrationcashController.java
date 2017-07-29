package io.renren.modules.yh.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.IntegrationcashEntity;
import io.renren.modules.yh.entity.enums.EnumIntegrationCash;
import io.renren.modules.yh.service.IntegrationcashService;




/**
 * 积分兑现表（销售人员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("integrationcash")
public class IntegrationcashController extends AbstractController {
	@Autowired
	private IntegrationcashService integrationcashService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("integrationcash:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<IntegrationcashEntity> integrationcashList = integrationcashService.queryList(query);
		int total = integrationcashService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(integrationcashList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("integrationcash:info")
	public R info(@PathVariable("id") Integer id){
		IntegrationcashEntity integrationcash = integrationcashService.queryObject(id);
		
		return R.ok().put("integrationcash", integrationcash);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("integrationcash:save")
	public R save(@RequestBody IntegrationcashEntity integrationcash){
		integrationcashService.save(integrationcash);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("integrationcash:update")
	public R update(@RequestBody IntegrationcashEntity integrationcash){
		integrationcashService.update(integrationcash);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("integrationcash:delete")
	public R delete(@RequestBody Integer[] ids){
		integrationcashService.deleteBatch(ids);
		
		return R.ok();
	}
	
	@RequestMapping("/apply")
	public R apply(@RequestParam("accountNum") String accountNum){
		
		BigDecimal account = new BigDecimal(accountNum);
		
		SysUserEntity user = this.getUser();
		
		IntegrationcashEntity integrationcashEntity = new IntegrationcashEntity();
		integrationcashEntity.setApplyTime(new Date());
		integrationcashEntity.setApplyUserId(user.getUserId());
		integrationcashEntity.setApplyUserName(user.getUsername());
		integrationcashEntity.setWithdrawalamount(account);
		integrationcashEntity.setWithdrawStatus(EnumIntegrationCash.APPLY.getStatus());
		
		integrationcashService.apply(integrationcashEntity,user);
		
		
		return R.ok();
	}
	
	
	@RequestMapping("/getIntegrationInfo")
	public R getIntegrationInfo(){
		
		SysUserEntity user = this.getUser();
		
		Map<String,Object> info = integrationcashService.getIntegrationInfo(user);
		
		return R.ok().put("info", info);
	}
	
	@RequestMapping("/agree")
	public R agree(@RequestParam("id") String id){
		
		IntegrationcashEntity integrationcashEntity = integrationcashService.queryObject(Integer.valueOf(id));
		
		integrationcashEntity.setOperateTime(new Date());
		integrationcashEntity.setWithdrawStatus(EnumIntegrationCash.ACCEPTED.getStatus());
		integrationcashEntity.setUserId(getUserId());	
		
		integrationcashService.update(integrationcashEntity);		
		
		return R.ok();
	}
	
	
	@RequestMapping("/complete")
	public R complete(@RequestParam("id") String id){
		
        IntegrationcashEntity integrationcashEntity = integrationcashService.queryObject(Integer.valueOf(id));
		
		integrationcashEntity.setOperateTime(new Date());
		integrationcashEntity.setWithdrawStatus(EnumIntegrationCash.COMPLETE.getStatus());
		integrationcashEntity.setUserId(getUserId());	
		
		integrationcashService.update(integrationcashEntity);		
		
		return R.ok();
	}
	
}
