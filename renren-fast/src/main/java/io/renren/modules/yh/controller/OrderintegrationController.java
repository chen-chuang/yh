package io.renren.modules.yh.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import io.renren.common.utils.DateUtils;
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
		
        Date dStartTime =null;
		
		Date dEndTime =null;
		
		String startTime = String.valueOf(params.get("startTime"));
		String endTime=String.valueOf(params.get("endTime"));
		
		if(StringUtils.isNotBlank(startTime)&&!startTime.equals("null")){
			startTime +=" 00:00:00";
			dStartTime = DateUtils.parse(startTime, DateUtils.DATE_TIME_PATTERN);
		}
		
		if(StringUtils.isNotBlank(endTime)&&!endTime.equals("null")){
			endTime+=" 00:00:00";
			dEndTime = DateUtils.parse(DateUtils.addByDay(DateUtils.parse(endTime, DateUtils.DATE_TIME_PATTERN), 1, DateUtils.DATE_TIME_PATTERN), DateUtils.DATE_TIME_PATTERN);
		}
		
		params.put("startTime", dStartTime);
		params.put("endTime", dEndTime);
		
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
	@RequiresPermissions("orderintegration:rebate")
	public R rebate(String startTime,String endTime,String deliveryUserId,String sumIntegration){
		
	    Date dStartTime =null;
		
		Date dEndTime =null;
		
		if(StringUtils.isNotBlank(startTime)){
			dStartTime = DateUtils.parse(startTime, DateUtils.DATE_TIME_PATTERN);
		}
		
		if(StringUtils.isNotBlank(endTime)){
			dEndTime = DateUtils.parse(DateUtils.addByDay(DateUtils.parse(endTime, DateUtils.DATE_TIME_PATTERN), 1, DateUtils.DATE_TIME_PATTERN), DateUtils.DATE_TIME_PATTERN);
		}
		
		orderintegrationService.rebate(dStartTime,dEndTime,deliveryUserId,sumIntegration);
		
		return R.ok();
	}
	
	@RequestMapping("/rebateByIds")
	@RequiresPermissions("orderintegration:rebateByIds")
	public R rebateByIds(String ids,String sumIntegration,String deliveryUserId){
		
		ids = ids.substring(1, ids.length()-1);
		ids = ids.replace("\"", "");
		
		String[] id = ids.split(",");
		Integer[] iid = new Integer[id.length];
		
		for(int i=0;i<id.length;i++){
			iid[i]=Integer.valueOf(id[i]);
		}
		
		orderintegrationService.rebateByIds(iid,sumIntegration,deliveryUserId);
		
		
		
		return R.ok();
	}
	
	
	@RequestMapping("/rebateDetailByIds")
	public R rebateDetailByIds(@RequestBody Integer[] ids){
		
		Map<String, Object> map = orderintegrationService.rebateDetailByIds(ids);
		
		return R.ok().put("detail", map);
	}
	
	@RequestMapping("/rebateDetail")
	public R rebateDetail(String startTime, String endTime, String deliveryUserId){
		
		Date dStartTime =null;
		
		Date dEndTime =null;
		
		if(StringUtils.isNotBlank(startTime)){
			dStartTime = DateUtils.parse(startTime, DateUtils.DATE_TIME_PATTERN);
		}
		
		if(StringUtils.isNotBlank(endTime)){
			dEndTime = DateUtils.parse(DateUtils.addByDay(DateUtils.parse(endTime, DateUtils.DATE_TIME_PATTERN), 1, DateUtils.DATE_TIME_PATTERN), DateUtils.DATE_TIME_PATTERN);
		}
		
		Map<String, Object> map = orderintegrationService.rebateDetail(dStartTime,dEndTime, deliveryUserId);
		
		return R.ok().put("detail", map);
	}
	
	
	
	
}
