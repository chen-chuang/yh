package io.renren.modules.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.AppValidateUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.EnterpriseDeatailInfoDTO;
import io.renren.modules.api.entity.dto.EnterpriseProductions;
import io.renren.modules.yh.entity.EnterpriseinfoEntity;
import io.renren.modules.yh.service.EnterpriseinfoService;
import io.renren.modules.yh.service.ProductService;

@RestController
@RequestMapping("/api")
public class ApiEnterpriseInfoController {
	
	@Autowired
	private EnterpriseinfoService enterpriseinfoService;
	
	@Autowired
	private ProductService productService;
	
	
	
	@AuthIgnore
	@RequestMapping("enterpriseList")
	public R enterpriseList(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			
			String userID = map.get("userID");
			String townID = map.get("townID");
			String limit = map.get("pageSize");
			String page = map.get("pageIndex");


			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userID", userID);
			params.put("townID", townID);
			params.put("limit", limit);
			params.put("page", page);
			
			params.put("enterpriseType", "2");
			
			Query query = new Query(params);

			List<EnterpriseinfoEntity> enterpriseInfoList = enterpriseinfoService.apiQueryList(query);
			
			return R.ok().put("info", enterpriseInfoList);
		}else{
			return R.error();
		}		
		
		
	}
	
	@AuthIgnore
	@RequestMapping("factoryList")
	public R factoryList(@RequestParam Map<String, String> map){
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			
			String userID = map.get("userID");
			String townID = map.get("townID");
			String limit = map.get("pageSize");
			String page = map.get("pageIndex");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userID", userID);
			params.put("townID", townID);
			params.put("limit", limit);
			params.put("page", page);
			
			params.put("enterpriseType", "1");
			
			Query query = new Query(params);
		
			List<EnterpriseinfoEntity> enterpriseInfoList = enterpriseinfoService.apiQueryList(query);
			
			return R.ok().put("info", enterpriseInfoList);
		}else{
			return R.error();
		}
	}
	
	
	@AuthIgnore
	@RequestMapping("enterpriseDetail")
	public R enterpriseDetail(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			String enterpriseId = map.get("enterpriseID");
			EnterpriseDeatailInfoDTO enterpriseDeatailInfoDTO = enterpriseinfoService.apiEnterpriseByID(enterpriseId);
			return R.ok().put("info", enterpriseDeatailInfoDTO);
		}else{
			return R.error();
		}
	}
	
	@AuthIgnore
	@RequestMapping("factoryDetail")
	public R factoryDetail(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			String enterpriseId = map.get("enterpriseID");
			EnterpriseDeatailInfoDTO enterpriseDeatailInfoDTO = enterpriseinfoService.apiEnterpriseByID(enterpriseId);
			return R.ok().put("info", enterpriseDeatailInfoDTO);
		}else{
			return R.error();
		}
	}
	
	@AuthIgnore
	@RequestMapping("enterpriseProducts")
	public R enterpriseProducts(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			
			String enterpriseID = map.get("enterpriseID");
			String limit = map.get("pageSize");
			String page = map.get("pageIndex");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enterpriseID", enterpriseID);
			params.put("limit", limit);
			params.put("page", page);
			
			Query query = new Query(params);
		
			List<EnterpriseProductions> enterpriseProductionsList = productService.apiEnterpriseProducts(query);
			return R.ok().put("info", enterpriseProductionsList);
			
		}else{
			return R.error();
		}
	}
	
	@AuthIgnore
	@RequestMapping("factoryProducts")
	public R factoryProducts(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			
			String enterpriseID = map.get("enterpriseID");
			String limit = map.get("pageSize");
			String page = map.get("pageIndex");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enterpriseID", enterpriseID);
			params.put("limit", limit);
			params.put("page", page);
			
			Query query = new Query(params);
		
			List<EnterpriseProductions> enterpriseProductionsList = productService.apiEnterpriseProducts(query);
			return R.ok().put("info", enterpriseProductionsList);
			
		}else{
			return R.error();
		}
	}
	
	
/*	@AuthIgnore
	@RequestMapping("enterpriseCity")
	public R enterpriseCity(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			
			String enterpriseID = map.get("enterpriseID");
			String limit = map.get("pageSize");
			String page = map.get("pageIndex");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enterpriseID", enterpriseID);
			params.put("limit", limit);
			params.put("page", page);
			
			Query query = new Query(params);
		
			List<EnterpriseProductions> enterpriseProductionsList = producttypeService.apiEnterpriseProducts(query);
			return R.ok().put("info", enterpriseProductionsList);
			
		}else{
			return R.error();
		}
	}
	
	@AuthIgnore
	@RequestMapping("factoryCity")
	public R factoryCity(@RequestParam Map<String, String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			
			String enterpriseID = map.get("enterpriseID");
			String limit = map.get("pageSize");
			String page = map.get("pageIndex");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enterpriseID", enterpriseID);
			params.put("limit", limit);
			params.put("page", page);
			
			Query query = new Query(params);
		
			List<EnterpriseProductions> enterpriseProductionsList = producttypeService.apiEnterpriseProducts(query);
			return R.ok().put("info", enterpriseProductionsList);
			
		}else{
			return R.error();
		}
	}*/

}
