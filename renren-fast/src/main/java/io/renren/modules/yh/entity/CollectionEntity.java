package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-24 14:18:20
 */
public class CollectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long userId;
	//
	private Long productId;

	/**
	 * 设置：
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 获取：
	 */
	public Long getProductId() {
		return productId;
	}
}
