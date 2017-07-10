package io.renren.modules.sys.entity;
import java.io.Serializable;
import java.util.Date;



/**
 * 行政区划表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 14:58:35
 */
public class RegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//行政区划名称
	private String name;
	//父ID
	private Integer pid;

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
}
