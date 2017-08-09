package io.renren.modules.api.entity.dto;

import java.io.Serializable;
import java.util.List;



/**
 * 行政区划表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class RegionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//行政区划名称
	private String name;
	
	private Integer pid;
	
	List<RegionDTO> sub;

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
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public List<RegionDTO> getSub() {
		return sub;
	}
	public void setSub(List<RegionDTO> sub) {
		this.sub = sub;
	}
}
