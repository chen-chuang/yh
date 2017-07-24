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

import io.renren.modules.yh.entity.CollectionEntity;
import io.renren.modules.yh.service.CollectionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * 
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-24 14:18:20
 */
@RestController
@RequestMapping("collection")
public class CollectionController {
	@Autowired
	private CollectionService collectionService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("collection:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CollectionEntity> collectionList = collectionService.queryList(query);
		int total = collectionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(collectionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("collection:info")
	public R info(@PathVariable("userId") Long userId){
		CollectionEntity collection = collectionService.queryObject(userId);
		
		return R.ok().put("collection", collection);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("collection:save")
	public R save(@RequestBody CollectionEntity collection){
		collectionService.save(collection);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("collection:update")
	public R update(@RequestBody CollectionEntity collection){
		collectionService.update(collection);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("collection:delete")
	public R delete(@RequestBody Long[] userIds){
		collectionService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
