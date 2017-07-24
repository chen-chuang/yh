package io.renren.modules.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.TownDTO;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.service.RegionService;

@RequestMapping("/api")
@RestController
public class ApiRecommendController {
	
	@Autowired
	private RegionService regionService;
	
	@AuthIgnore
	@RequestMapping("town")
	public R town(HttpServletRequest request){
		
		String areaID = request.getParameter("areaID");
		List<TownDTO> region = regionService.apiTown(areaID);
		
		return R.ok().put("info", region);
		
	}
	
	@AuthIgnore
	@RequestMapping("submitOrder")
	public R submitOrder(OrderEntity orderEntity){
		return R.ok();
	}
	
	@AuthIgnore
	@RequestMapping("hotSaleProduction")
	public R hotSaleProduction(OrderEntity orderEntity){
		return R.ok();
	}
	

}
