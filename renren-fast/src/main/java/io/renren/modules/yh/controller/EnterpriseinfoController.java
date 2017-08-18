package io.renren.modules.yh.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
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

import io.renren.common.utils.BaiduMap;
import io.renren.common.utils.CommonUtils;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.FileUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.yh.entity.EnterpriseinfoEntity;
import io.renren.modules.yh.entity.enums.EnumPermission;
import io.renren.modules.yh.service.EnterpriseinfoService;




/**
 * 企业信息表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("enterpriseinfo")
public class EnterpriseinfoController extends AbstractController {
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
	public R save(EnterpriseinfoEntity enterpriseinfo,@RequestParam(value="picFile",required=false) MultipartFile file){
		
		SysUserEntity userEntity = this.getUser();
		if(userEntity.getUserPermission().equals(EnumPermission.ADMIN.getType())){
			int count = enterpriseinfoService.validateOnlyAgency(enterpriseinfo.getEnterpriseAreaId(),null,enterpriseinfo.getEnterpriseType());
			if(count>0){
				String mString = enterpriseinfo.getEnterpriseType().equals(1)?"生产厂家":"区域经销商";
				return R.error("该区域已存在一个"+mString+"，不允许录入！");
			}
		}
		
		ValidatorUtils.validateEntity(enterpriseinfo, AddGroup.class);
		
		if(file!=null&&StringUtils.isNotBlank(file.getOriginalFilename())){
			try {
				String orginalFileName = file.getOriginalFilename();
				String filename = CommonUtils.generateFileName(orginalFileName);
				String directory = ConfigConstant.ENTERPRISE_PIC_DIR;
				FileUtils.makeDir(directory);
				String filepath = Paths.get(directory, filename).toString();
			
			    BufferedOutputStream stream =
			        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			    stream.write(file.getBytes());
			    stream.close();
			    
			    enterpriseinfo.setEnterpriseImageUrl("/upload/"+filename);
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		}	
		
		String ll = BaiduMap.getGeocoderLatitude(enterpriseinfo.getEnterpriseAddress());
		if(StringUtils.isNotBlank(ll)){
			String[] arrStr = ll.split(",");
			enterpriseinfo.setEnterpriseLongitude(arrStr[0]);
			enterpriseinfo.setEnterpriseLatitude(arrStr[1]);
		}
		
		enterpriseinfo.setEnterId(userEntity.getUserId());
		enterpriseinfo.setEnterName(userEntity.getUsername());
		
		enterpriseinfoService.save(enterpriseinfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("enterpriseinfo:update")
	public R update(EnterpriseinfoEntity enterpriseinfo,@RequestParam(value="picFile",required=false) MultipartFile file){		
		
		SysUserEntity userEntity = this.getUser();
		if(userEntity.getUserPermission().equals(EnumPermission.ADMIN.getType())){
			int count = enterpriseinfoService.validateOnlyAgency(enterpriseinfo.getEnterpriseAreaId(),null,enterpriseinfo.getEnterpriseType());
			if(count>1){
				String mString = enterpriseinfo.getEnterpriseType().equals(1)?"生产厂家":"区域经销商";
				return R.error("该区域已存在一个"+mString+"，不允许录入！");
			}
		}
		
		ValidatorUtils.validateEntity(enterpriseinfo, UpdateGroup.class);
		
		if(file!=null&&StringUtils.isNotBlank(file.getOriginalFilename())){
			try {
				String orginalFileName = file.getOriginalFilename();
				String filename = CommonUtils.generateFileName(orginalFileName);
				String directory = ConfigConstant.ENTERPRISE_PIC_DIR;
				FileUtils.makeDir(directory);
				String filepath = Paths.get(directory, filename).toString();
			
			    BufferedOutputStream stream =
			        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			    stream.write(file.getBytes());
			    stream.close();
			    
			    enterpriseinfo.setEnterpriseImageUrl("/upload/"+filename);
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		}	
		
		String ll = BaiduMap.getGeocoderLatitude(enterpriseinfo.getEnterpriseAddress());
		if(StringUtils.isNotBlank(ll)){
			String[] arrStr = ll.split(",");
			enterpriseinfo.setEnterpriseLongitude(arrStr[0]);
			enterpriseinfo.setEnterpriseLatitude(arrStr[1]);
		}
		
		enterpriseinfo.setEnterId(userEntity.getUserId());
		enterpriseinfo.setEnterName(userEntity.getUsername());
		
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
	
	@RequestMapping("/getByName")
	public R getByName(@RequestParam("enterpriseName") String enterpriseName){
		List<Map<String, Object>> enterpriseinfo = enterpriseinfoService.getByName(enterpriseName);
		
		return R.ok().put("enterpriseinfo", enterpriseinfo);
	}
	

	
}
