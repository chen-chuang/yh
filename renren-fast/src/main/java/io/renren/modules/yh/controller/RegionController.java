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

import io.renren.modules.yh.entity.RegionEntity;
import io.renren.modules.yh.service.RegionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 行政区划表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
@RestController
@RequestMapping("region")
public class RegionController {
	@Autowired
	private RegionService regionService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("region:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RegionEntity> regionList = regionService.queryList(query);
		int total = regionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(regionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("region:info")
	public R info(@PathVariable("id") Integer id){
		RegionEntity region = regionService.queryObject(id);
		
		return R.ok().put("region", region);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("region:save")
	public R save(@RequestBody RegionEntity region){
		regionService.save(region);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("region:update")
	public R update(@RequestBody RegionEntity region){
		regionService.update(region);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("region:delete")
	public R delete(@RequestBody Integer[] ids){
		
		String msg = regionService.deleteBatch(ids);
		
		return R.ok(msg);
	}
	
	@RequestMapping("/getCitys/{pid}")
	/**
	 * 得到下一级行政区域
	 *@描述
	 *@param pid
	 *@return
	 *@作者 ccchen
	 *@时间 2017年7月19日上午10:11:47
	 */
	public R getCitys(@PathVariable("pid") int pid){
		List<RegionEntity> regionList = regionService.queryListByPid(pid);
		
		return R.ok().put("citys", regionList);
	}
		
	@RequestMapping("/getFullRegion/{id}")
	/**
	 * 获得<省,市, , ,>这种形式，由下往上
	 *@描述
	 *@param id
	 *@return
	 *@作者 ccchen
	 *@时间 2017年7月19日上午10:10:28
	 */
	public R getFullRegion(@PathVariable("id") int id){
		
		List<String> region = regionService.getFullRegion(id);
		
		return R.ok().put("region", region);
		
	}
	
}
