package io.renren.modules.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.api.annotation.AuthIgnore;
import io.renren.modules.api.entity.dto.OrderDetailInfo;
import io.renren.modules.yh.service.OrderService;

@RequestMapping("/api")
@RestController
public class ApiOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@AuthIgnore
	@RequestMapping("orderList")
	public R orderList(HttpServletRequest request){
		String userID = request.getParameter("userID");
		String orderType = request.getParameter("orderType");
		
		List<OrderDetailInfo> info = orderService.apiOrderList(userID,orderType);
		
		return R.ok().put("info", info);
	}

}
