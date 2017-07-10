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
import io.renren.modules.sys.entity.ProductEntity;
import io.renren.modules.sys.service.ProductService;



/**
 * 产品表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
@RestController
@RequestMapping("product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("product:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ProductEntity> productList = productService.queryList(query);
		int total = productService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(productList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{productId}")
	@RequiresPermissions("product:info")
	public R info(@PathVariable("productId") Long productId){
		ProductEntity product = productService.queryObject(productId);
		
		return R.ok().put("product", product);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("product:save")
	public R save(@RequestBody ProductEntity product){
		productService.save(product);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("product:update")
	public R update(@RequestBody ProductEntity product){
		productService.update(product);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("product:delete")
	public R delete(@RequestBody Long[] productIds){
		productService.deleteBatch(productIds);
		
		return R.ok();
	}
	
}
