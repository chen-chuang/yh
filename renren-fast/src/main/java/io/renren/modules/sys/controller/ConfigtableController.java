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
import io.renren.modules.sys.entity.ConfigtableEntity;
import io.renren.modules.sys.service.ConfigtableService;



/**
 * 配置表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
@RestController
@RequestMapping("configtable")
public class ConfigtableController {
	@Autowired
	private ConfigtableService configtableService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("configtable:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ConfigtableEntity> configtableList = configtableService.queryList(query);
		int total = configtableService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(configtableList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("configtable:info")
	public R info(@PathVariable("id") Integer id){
		ConfigtableEntity configtable = configtableService.queryObject(id);
		
		return R.ok().put("configtable", configtable);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("configtable:save")
	public R save(@RequestBody ConfigtableEntity configtable){
		configtableService.save(configtable);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("configtable:update")
	public R update(@RequestBody ConfigtableEntity configtable){
		configtableService.update(configtable);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("configtable:delete")
	public R delete(@RequestBody Integer[] ids){
		configtableService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
