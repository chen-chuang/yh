package io.renren.modules.yh.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.entity.enums.EnumPermission;
import io.renren.modules.yh.service.OrderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 订单表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("order")
public class OrderController extends AbstractController{
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("order:list")
	public R list(@RequestParam Map<String, Object> params){
		
		SysUserEntity user = getUser();
		if(user.getUserPermission().equals(EnumPermission.AGENCY.getType())){
			params.put("userId", getUserId());
		}
		
		if(user.getUserPermission().equals(EnumPermission.DELIVERY_F.getType())){
			params.put("userId", user.getBelongToAgencyId());
		}
		
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
	
	@RequestMapping("/dispatch")
	public R dispatch(String orderId,String userId){
	    orderService.dispatch(orderId,userId);
		return R.ok();
	}
	
	@RequestMapping("/complete")
	public R complete(String orderId){
		orderService.complete(orderId);
		return R.ok();
	}
	
	
	@RequestMapping("/getDeliveryPerson")
	public R getDeliveryPerson(){
		
		Long userId =null;
		SysUserEntity user = getUser();
		if(user.getUserPermission().equals(EnumPermission.AGENCY.getType())){
			userId = getUserId();
		}
		
		if(user.getUserPermission().equals(EnumPermission.DELIVERY_F.getType())){
			userId = user.getBelongToAgencyId();
		}
		
		List<Map<String, Object>> deliveryPerson = sysUserService.getDeliveryPerson(userId);
		return R.ok().put("deliveryPerson", deliveryPerson);
	}
	
}
