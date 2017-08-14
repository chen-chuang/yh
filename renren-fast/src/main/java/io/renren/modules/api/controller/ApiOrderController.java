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
import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.api.entity.dto.WithDrawDTO;
import io.renren.modules.yh.service.OrderService;

@RequestMapping("/api")
@RestController
public class ApiOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@AuthIgnore
	@RequestMapping("orderList")
	public R orderList(@RequestParam Map<String,String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			String userID = map.get("userID");
			String page = map.get("pageIndex");
			String limit = map.get("pageSize");
			String orderType =  map.get("orderType");
			

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userID", userID);
			params.put("limit", limit);
			params.put("page", page);
			params.put("orderType", orderType);
			
			Query query = new Query(params);

			List<OrderDetailInfo> info = orderService.apiOrderList(params);
			
			return R.ok().put("info", info);	
		}else{
			return R.error();
		}	
	}
	
	@AuthIgnore
	@RequestMapping("delWaitingPayOrder")
	public R delWaitingPayOrder(@RequestParam Map<String,String> map){
		
		String sign = map.get("sign");
		map.remove("sign");
		
        String websign = AppValidateUtils.getSign(map);
		
		if(websign.equals(sign)){
			String userID = map.get("userID");
			String orderID = map.get("orderID");
			

			orderService.apiDelWaitingPayOrder(userID,orderID);
			
			return R.ok();	
		}else{
			return R.error();
		}	
	}

}
