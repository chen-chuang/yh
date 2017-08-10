package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;



/**
 * 产品分类表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class ProducttypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	
	@NotBlank(message="分类名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String type;
	
	private String imageUrl;
	
	private Integer showInHomepage;
	
	private String enterId;

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
	 * 设置：
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：
	 */
	public String getType() {
		return type;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getShowInHomepage() {
		return showInHomepage;
	}
	public void setShowInHomepage(Integer showInHomepage) {
		this.showInHomepage = showInHomepage;
	}
	public String getEnterId() {
		return enterId;
	}
	public void setEnterId(String enterId) {
		this.enterId = enterId;
	}
}
