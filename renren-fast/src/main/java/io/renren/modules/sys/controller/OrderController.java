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
import io.renren.modules.sys.entity.OrderEntity;
import io.renren.modules.sys.service.OrderService;



/**
 * 订单表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:36
 */
@RestController
@RequestMapping("order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("order:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<OrderEntity> orderList = orderService.queryList(query);
		int total = orderService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(orderList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{orderId}")
	@RequiresPermissions("order:info")
	public R info(@PathVariable("orderId") String orderId){
		OrderEntity order = orderService.queryObject(orderId);
		
		return R.ok().put("order", order);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("order:save")
	public R save(@RequestBody OrderEntity order){
		orderService.save(order);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("order:update")
	public R update(@RequestBody OrderEntity order){
		orderService.update(order);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("order:delete")
	public R delete(@RequestBody String[] orderIds){
		orderService.deleteBatch(orderIds);
		
		return R.ok();
	}
	
}
