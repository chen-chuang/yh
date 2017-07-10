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

import io.renren.modules.yh.entity.EnterpriseinfoEntity;
import io.renren.modules.yh.service.EnterpriseinfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 企业信息表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("enterpriseinfo")
public class EnterpriseinfoController {
	@Autowired
	private EnterpriseinfoService enterpriseinfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("enterpriseinfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<EnterpriseinfoEntity> enterpriseinfoList = enterpriseinfoService.queryList(query);
		int total = enterpriseinfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(enterpriseinfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{enterpriseId}")
	@RequiresPermissions("enterpriseinfo:info")
	public R info(@PathVariable("enterpriseId") Long enterpriseId){
		EnterpriseinfoEntity enterpriseinfo = enterpriseinfoService.queryObject(enterpriseId);
		
		return R.ok().put("enterpriseinfo", enterpriseinfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("enterpriseinfo:save")
	public R save(@RequestBody EnterpriseinfoEntity enterpriseinfo){
		enterpriseinfoService.save(enterpriseinfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("enterpriseinfo:update")
	public R update(@RequestBody EnterpriseinfoEntity enterpriseinfo){
		enterpriseinfoService.update(enterpriseinfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("enterpriseinfo:delete")
	public R delete(@RequestBody Long[] enterpriseIds){
		enterpriseinfoService.deleteBatch(enterpriseIds);
		
		return R.ok();
	}
	
}
