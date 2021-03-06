package io.renren.modules.yh.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.ProductEntity;
import io.renren.modules.yh.entity.enums.EnumPermission;
import io.renren.modules.yh.service.EnterpriseinfoService;
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
public class ProductController extends AbstractController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private EnterpriseinfoService enterpriseinfoService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("product:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		SysUserEntity userEntity = this.getUser();		
		Long currentUserId  = userEntity.getUserId();
		if(userEntity.getUserPermission().equals(EnumPermission.ADMIN.getType())){
			params.put("enterType", 1);
		}else{
			params.put("currentUserId", currentUserId);
		}		
		
		
        Query query = new Query(params);

		List<ProductEntity> productList = productService.queryList(query);
		int total = productService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(productList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	@RequestMapping("/pclist")
	public R pcList(@RequestParam Map<String, Object> params){
		//查询列表数据
		Long currentUserId  = this.getUserId();
		
		params.put("currentUserId", currentUserId);
        Query query = new Query(params);

		List<ProductEntity> productList = productService.queryPcList(query);
		int total = productList.size();
		
		PageUtils pageUtil = new PageUtils(productList, total,total,1);
		
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
		
		SysUserEntity currentUser = this.getUser();
		
		if(picFile!=null&&StringUtils.isNotBlank(picFile.getOriginalFilename())){
			try {
				String orginalFileName = picFile.getOriginalFilename();
				String filename = CommonUtils.generateFileName(orginalFileName);
				//真是目录
				String directory = ConfigConstant.ENTERPRISE_PRODUCT_PIC_DIR;
				FileUtils.makeDir(directory);
				String filepath = Paths.get(directory, filename).toString();
			
			    BufferedOutputStream stream =
			        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			    stream.write(picFile.getBytes());
			    stream.close();
			    
			    //虚拟目录
			    product.setProductPictureUrl("/upload/"+filename);
			    
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		}		
		
		
		if(videoFile!=null&&StringUtils.isNotBlank(videoFile.getOriginalFilename())){
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
			    
			    product.setProductVideoUrl("/upload/"+filename);
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		}	 
		
		if(currentUser.getUserPermission().equals(EnumPermission.ADMIN.getType())){
			product.setEnterType(1);
		}
		
		if(currentUser.getUserPermission().equals(EnumPermission.AGENCY.getType())){
			product.setEnterType(2);
		}
		
		if(currentUser.getUserPermission().equals(EnumPermission.FACTORY.getType())){
			product.setEnterType(3);
		}
		
		product.setEnterPersonId(currentUser.getUserId());
		product.setEnterName(currentUser.getUsername());
		
		
		productService.save(product);		
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("product:update")
	public R update(ProductEntity product,
			@RequestParam(value="picFile",required=false) MultipartFile picFile,
			@RequestParam(value="videoFile",required=false) MultipartFile videoFile){
		
		SysUserEntity currentUser = this.getUser();
		
		if(picFile!=null&&StringUtils.isNotBlank(picFile.getOriginalFilename())){
			try {
				String orginalFileName = picFile.getOriginalFilename();
				String filename = CommonUtils.generateFileName(orginalFileName);
				//真是目录
				String directory = ConfigConstant.ENTERPRISE_PRODUCT_PIC_DIR;
				FileUtils.makeDir(directory);
				String filepath = Paths.get(directory, filename).toString();
			
			    BufferedOutputStream stream =
			        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			    stream.write(picFile.getBytes());
			    stream.close();
			    
			    //虚拟目录
			    product.setProductPictureUrl("/upload/"+filename);
			    
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		}		
		
		
		if(videoFile!=null&&StringUtils.isNotBlank(videoFile.getOriginalFilename())){
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
			    
			    product.setProductVideoUrl("/upload/"+filename);
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		}	 
		
		if(currentUser.getUserPermission().equals(EnumPermission.ADMIN)){
			product.setEnterType(1);
		}
		
		if(currentUser.getUserPermission().equals(EnumPermission.AGENCY)){
			product.setEnterType(2);
		}
		
		if(currentUser.getUserPermission().equals(EnumPermission.FACTORY.getType())){
			product.setEnterType(3);
		}
		
		product.setEnterPersonId(currentUser.getUserId());
		product.setEnterName(currentUser.getUsername());
		
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
	
	
	@RequestMapping("/getEnterprise")
	public R getEnterprise(){
		
		List<Map<String,Object>> enterprises = enterpriseinfoService.getEnterprise();
		
		List<Map<String,Object>> citys = new ArrayList<>();
		
		
		for(Map<String, Object> hashMap : enterprises){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", hashMap.get("enterprise_id"));
			map.put("name", hashMap.get("enterprise_name"));
			citys.add(map);
		}
		
		return R.ok().put("citys", citys);
	}
	
}
