package io.renren.modules.yh.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.renren.modules.yh.entity.ProductEntity;
import io.renren.modules.yh.service.ProductService;
import io.renren.common.utils.CommonUtils;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.FileUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 产品表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
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
	public R save(ProductEntity product,
			@RequestParam(value="picFile",required=false) MultipartFile picFile,
			@RequestParam(value="videoFile",required=false) MultipartFile videoFile){
		
		if(picFile!=null){
			try {
				String orginalFileName = picFile.getOriginalFilename();
				String filename = CommonUtils.generateFileName(orginalFileName);
				String directory = ConfigConstant.ENTERPRISE_PRODUCT_PIC_DIR;
				FileUtils.makeDir(directory);
				String filepath = Paths.get(directory, filename).toString();
			
			    BufferedOutputStream stream =
			        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			    stream.write(picFile.getBytes());
			    stream.close();
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		}		
		
		
		if(videoFile!=null){
			try {
				String orginalFileName = videoFile.getOriginalFilename();
				String filename = CommonUtils.generateFileName(orginalFileName);
				String directory = ConfigConstant.ENTERPRISE_PRODUCT_VIDEO_DIR;
				FileUtils.makeDir(directory);
				String filepath = Paths.get(directory, filename).toString();
			
			    BufferedOutputStream stream =
			        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			    stream.write(videoFile.getBytes());
			    stream.close();
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		}	 
		
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
