package io.renren.modules.yh.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.yh.entity.OrderdetailEntity;
import io.renren.modules.yh.service.OrderdetailService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 订单明细表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("orderdetail")
public class OrderdetailController {
	@Autowired
	private OrderdetailService orderdetailService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("orderdetail:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<OrderdetailEntity> orderdetailList = orderdetailService.queryList(query);
		int total = orderdetailService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(orderdetailList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{orderId}")
	@RequiresPermissions("orderdetail:info")
	public R info(@PathVariable("orderId") String orderId){
		OrderdetailEntity orderdetail = orderdetailService.queryObject(orderId);
		
		return R.ok().put("orderdetail", orderdetail);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("orderdetail:save")
	public R save(@RequestBody OrderdetailEntity orderdetail){
		orderdetailService.save(orderdetail);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("orderdetail:update")
	public R update(@RequestBody OrderdetailEntity orderdetail){
		orderdetailService.update(orderdetail);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("orderdetail:delete")
	public R delete(@RequestBody String[] orderIds){
		orderdetailService.deleteBatch(orderIds);
		
		return R.ok();
	}
	
}
