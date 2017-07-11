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

import io.renren.modules.yh.entity.ProducttypeEntity;
import io.renren.modules.yh.service.ProducttypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 产品分类表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("producttype")
public class ProducttypeController {
	@Autowired
	private ProducttypeService producttypeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("producttype:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ProducttypeEntity> producttypeList = producttypeService.queryList(query);
		int total = producttypeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(producttypeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("producttype:info")
	public R info(@PathVariable("id") Integer id){
		ProducttypeEntity producttype = producttypeService.queryObject(id);
		
		return R.ok().put("producttype", producttype);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("producttype:save")
	public R save(@RequestBody ProducttypeEntity producttype){
		producttypeService.save(producttype);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("producttype:update")
	public R update(@RequestBody ProducttypeEntity producttype){
		producttypeService.update(producttype);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("producttype:delete")
	public R delete(@RequestBody Integer[] ids){
		producttypeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}