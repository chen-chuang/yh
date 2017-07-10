package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 订单明细表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class OrderdetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String orderId;
	//
	private Long productId;
	//
	private Long productNum;
	//
	private String productName;
	//
	private BigDecimal productPrice;
	//
	private BigDecimal productSumPrice;
	//
	private Long enterpriseId;
	//
	private String enterpriseName;
	//
	private String productPictureUrl;

	/**
	 * 设置：
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：
	 */
	public String getOrderId() {
		return orderId;
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
	/**
	 * 设置：
	 */
	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}
	/**
	 * 获取：
	 */
	public Long getProductNum() {
		return productNum;
	}
	/**
	 * 设置：
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置：
	 */
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	/**
	 * 设置：
	 */
	public void setProductSumPrice(BigDecimal productSumPrice) {
		this.productSumPrice = productSumPrice;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getProductSumPrice() {
		return productSumPrice;
	}
	/**
	 * 设置：
	 */
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	/**
	 * 获取：
	 */
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	/**
	 * 设置：
	 */
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	/**
	 * 获取：
	 */
	public String getEnterpriseName() {
		return enterpriseName;
	}
	/**
	 * 设置：
	 */
	public void setProductPictureUrl(String productPictureUrl) {
		this.productPictureUrl = productPictureUrl;
	}
	/**
	 * 获取：
	 */
	public String getProductPictureUrl() {
		return productPictureUrl;
	}
}
