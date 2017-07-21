package io.renren.modules.yh.controller;

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

import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.ConfigtableEntity;
import io.renren.modules.yh.service.ConfigtableService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 配置表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:49:26
 */
@RestController
@RequestMapping("configtable")
public class ConfigtableController extends AbstractController {
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
		
		
		SysUserEntity userEntity = this.getUser();
		
		configtable.setConfigCreateTime(new Date());
		configtable.setConfigReginName(userEntity.getUserArea());
		configtable.setConfigRegionId(Integer.valueOf(userEntity.getAreaId()));
		configtable.setConfigUserId(userEntity.getUserId());
		configtable.setConfigUserName(userEntity.getUsername());
		
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
