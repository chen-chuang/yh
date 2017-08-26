package io.renren.modules.yh.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.yh.entity.AccountEntity;
import io.renren.modules.yh.service.AccountService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 资金账户表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-11 17:54:19
 */
@RestController
@RequestMapping("account")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("account:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<AccountEntity> accountList = accountService.queryList(query);
		int total = accountService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(accountList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("account:info")
	public R info(@PathVariable("id") Integer id){
		AccountEntity account = accountService.queryObject(id);
		
		return R.ok().put("account", account);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("account:save")
	public R save(@RequestBody AccountEntity account){
		accountService.save(account);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("account:update")
	public R update(@RequestBody AccountEntity account){
		accountService.update(account);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("account:delete")
	public R delete(@RequestBody Integer[] ids){
		accountService.deleteBatch(ids);
		
		return R.ok();
	}
	
	
	@RequestMapping("/detailInfo")
	public R detailInfo(){
		R r = accountService.detailInfo();
		
		return r;
	}
	
	
}
