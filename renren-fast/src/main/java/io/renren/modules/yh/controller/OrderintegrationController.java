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

import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.yh.entity.OrderintegrationEntity;
import io.renren.modules.yh.service.OrderintegrationService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 订单积分表（销售人员、配送员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("orderintegration")
public class OrderintegrationController extends AbstractController {
	@Autowired
	private OrderintegrationService orderintegrationService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("orderintegration:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		params.put("userId", getUserId());
		
        Query query = new Query(params);

		List<OrderintegrationEntity> orderintegrationList = orderintegrationService.queryList(query);
		int total = orderintegrationService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(orderintegrationList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("orderintegration:info")
	public R info(@PathVariable("id") Integer id){
		OrderintegrationEntity orderintegration = orderintegrationService.queryObject(id);
		
		return R.ok().put("orderintegration", orderintegration);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("orderintegration:save")
	public R save(@RequestBody OrderintegrationEntity orderintegration){
		orderintegrationService.save(orderintegration);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("orderintegration:update")
	public R update(@RequestBody OrderintegrationEntity orderintegration){
		orderintegrationService.update(orderintegration);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("orderintegration:delete")
	public R delete(@RequestBody Integer[] ids){
		orderintegrationService.deleteBatch(ids);
		
		return R.ok();
	}
	
	@RequestMapping("/rebate")
	public R rebate(String startTime,String endTime,String deliveryUserId){
		
		orderintegrationService.rebate(startTime,endTime,deliveryUserId);
		
		return R.ok();
	}
	
	@RequestMapping("/rebateByIds")
	public R rebateByIds(@RequestBody Integer[] ids){
		
		orderintegrationService.rebateByIds(ids);
		
		return R.ok();
	}
	
	
	@RequestMapping("/rebateDetailByIds")
	public R rebateDetailByIds(@RequestBody Integer[] ids){
		
		orderintegrationService.rebateDetailByIds(ids);
		
		return R.ok();
	}
	
	@RequestMapping("/rebateDetail")
	public R rebateDetail(String startTime, String endTime, String deliveryUserId){
		
		orderintegrationService.rebateDetail(startTime,endTime, deliveryUserId);
		
		return R.ok();
	}
	
	
	
	
}
