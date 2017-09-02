package io.renren.modules.yh.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.yh.entity.OrderEntity;
import io.renren.modules.yh.entity.dto.DeliveryOrderDTO;
import io.renren.modules.yh.entity.enums.EnumPermission;
import io.renren.modules.yh.service.OrderService;




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
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
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
		
		params.put("orderCreateType", 1);
		
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
	@RequiresPermissions("order:dispatch")
	public R dispatch(String orderId){
	    R r = orderService.dispatch(orderId);
		return r;
	}
	
	@RequestMapping("/complete")
	@RequiresPermissions("order:complete")
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
	
	@RequestMapping("/choiceDelivery")
	@RequiresPermissions("order:choiceDelivery")
	public R choiceDelivery(String orderId,String userId){
		
		SysUserEntity user = sysUserService.queryObject(Long.valueOf(userId));
		SysUserEntity userF = this.getUser();
		
		OrderEntity order = orderService.queryObject(orderId);
		order.setDeliveryUserId(user.getUserId());
		order.setDeliveryUserName(user.getUsername());
		
		order.setDeliveryFUserId(userF.getUserId());
		order.setDeliveryFUserName(userF.getUsername());
		
		orderService.update(order);
		
		
		//初始化client,apikey作为所有请求的默认值(可以为空)
		YunpianClient clnt = new YunpianClient(ConfigConstant.YUPIAN_SMS_APIKEY).init();

		//修改账户信息API
		Map<String, String> param = clnt.newParam(2);
		param.put(YunpianClient.MOBILE, user.getMobile());
		param.put(YunpianClient.TEXT, "【恒通烟花易购】订单编号"+order.getOrderId()+"已由发货人"+userF.getUsername()+"选择您来配送，请尽快完成配送。");
		Result<SmsSingleSend> r = clnt.sms().single_send(param);
		clnt.close(); 
		LOG.info(r.toString());
		System.out.println(r.toString());
		
		return R.ok();
		
		
	}	
	
	@RequestMapping("/print")
	@RequiresPermissions("order:print")
	public R print(String orderId){
		
		DeliveryOrderDTO order = orderService.printDelivery(orderId);
		
		return R.ok().put("order", order);
		
	}
	
	
	
}
