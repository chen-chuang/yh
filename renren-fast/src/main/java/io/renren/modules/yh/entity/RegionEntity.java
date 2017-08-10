package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;



/**
 * 行政区划表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class RegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//行政区划名称
	@NotBlank(message="行政区域不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	//父ID
	private Integer pid;
	
	List<?> list;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：行政区划名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：行政区划名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：父ID
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父ID
	 */
	public Integer getPid() {
		return pid;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
}
