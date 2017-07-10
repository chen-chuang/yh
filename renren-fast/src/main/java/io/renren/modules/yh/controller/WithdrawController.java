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

import io.renren.modules.yh.entity.WithdrawEntity;
import io.renren.modules.yh.service.WithdrawService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 提现记录表（区域经销商）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("withdraw")
public class WithdrawController {
	@Autowired
	private WithdrawService withdrawService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("withdraw:list")
	public R list(@RequestParam Map<String, Object> params){
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
	
}
