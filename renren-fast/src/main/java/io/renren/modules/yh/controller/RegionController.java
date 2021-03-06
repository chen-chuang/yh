package io.renren.modules.yh.controller;

import java.util.HashMap;
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
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.yh.entity.RegionEntity;
import io.renren.modules.yh.service.RegionService;




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
		ValidatorUtils.validateEntity(region, AddGroup.class);
		
		int count = regionService.onlyId(region.getId());
		if(count>0){
			return R.error("已存在编码为"+region.getId()+"的行政区域！");					
		}
		
		regionService.save(region);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("region:update")
	public R update(@RequestBody RegionEntity region){
		ValidatorUtils.validateEntity(region, UpdateGroup.class);
		
		regionService.update(region);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("region:delete")
	public R delete(@RequestBody Integer id){
		Integer[] ids = new Integer[]{id};
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
	
	@RequestMapping("/getAll")
	public List<RegionEntity> getAll(){
		List<RegionEntity> regionList = regionService.queryList(new HashMap<String, Object>());
		
        return regionList;
    }
	
	
	@RequestMapping("/getParentKeyValue")
	public R getParentKeyValue(String id){
		Map<String, Object> map = regionService.getParentKeyValue(id);
		return R.ok().put("info", map);
	}
	
}
