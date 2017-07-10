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
import io.renren.modules.sys.entity.CcountEntity;
import io.renren.modules.sys.service.CcountService;



/**
 * 资金账户表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 15:04:32
 */
@RestController
@RequestMapping("ccount")
public class CcountController {
	@Autowired
	private CcountService ccountService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ccount:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CcountEntity> ccountList = ccountService.queryList(query);
		int total = ccountService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ccountList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ccount:info")
	public R info(@PathVariable("id") Integer id){
		CcountEntity ccount = ccountService.queryObject(id);
		
		return R.ok().put("ccount", ccount);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ccount:save")
	public R save(@RequestBody CcountEntity ccount){
		ccountService.save(ccount);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ccount:update")
	public R update(@RequestBody CcountEntity ccount){
		ccountService.update(ccount);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ccount:delete")
	public R delete(@RequestBody Integer[] ids){
		ccountService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
