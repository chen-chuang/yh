package io.renren.modules.sys.controller;

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
import io.renren.modules.sys.entity.IntegrationcashEntity;
import io.renren.modules.sys.service.IntegrationcashService;



/**
 * 积分兑现表（销售人员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
@RestController
@RequestMapping("integrationcash")
public class IntegrationcashController {
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
	
}
