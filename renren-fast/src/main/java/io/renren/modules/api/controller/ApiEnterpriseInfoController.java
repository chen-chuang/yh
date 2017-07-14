package io.renren.modules.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.EnterpriseDeatailInfoDTO;
import io.renren.modules.yh.entity.EnterpriseinfoEntity;
import io.renren.modules.yh.service.EnterpriseinfoService;

@RestController
@RequestMapping("/api")
public class ApiEnterpriseInfoController {
	
	@Autowired
	private EnterpriseinfoService enterpriseinfoService;
	
	@AuthIgnore
	@RequestMapping("enterpriseList")
	public R enterpriseList(HttpServletRequest request){
		String userID = request.getParameter("userID");
		String townID = request.getParameter("townID");
		String limit = request.getParameter("pageSize");
		String page = request.getParameter("pageIndex");


		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("townID", townID);
		params.put("limit", limit);
		params.put("page", page);
		
		params.put("enterpriseType", "2");
		
		Query query = new Query(params);

		List<EnterpriseinfoEntity> enterpriseInfoList = enterpriseinfoService.apiQueryList(query);
		
		return R.ok().put("info", enterpriseInfoList);
	}
	
	@AuthIgnore
	@RequestMapping("factoryList")
	public R factoryList(HttpServletRequest request){
		String userID = request.getParameter("userID");
		String townID = request.getParameter("townID");
		String limit = request.getParameter("pageSize");
		String page = request.getParameter("pageIndex");


		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("townID", townID);
		params.put("limit", limit);
		params.put("page", page);
		
		params.put("enterpriseType", "1");
		
		Query query = new Query(params);

		List<EnterpriseinfoEntity> enterpriseInfoList = enterpriseinfoService.apiQueryList(query);
		
		return R.ok().put("info", enterpriseInfoList);
	}
	
	
	@AuthIgnore
	@RequestMapping("enterpriseDetail")
	public R enterpriseDetail(HttpServletRequest request){
		String enterpriseId = request.getParameter("enterpriseID");
		EnterpriseDeatailInfoDTO enterpriseDeatailInfoDTO = enterpriseinfoService.apiEnterpriseByID(enterpriseId);
		return R.ok().put("info", enterpriseDeatailInfoDTO);
	}

}
