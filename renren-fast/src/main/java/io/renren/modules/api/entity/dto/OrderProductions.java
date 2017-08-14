package io.renren.modules.api.entity.dto;

import java.math.BigDecimal;

public class OrderProductions {
	
	private Long productId;
	
	private String productName;
	
	private BigDecimal productPrice;
	
	private String productPictureUrl;
	
	private int orderProductionsCount;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductPictureUrl() {
		return productPictureUrl;
	}

	public void setProductPictureUrl(String productPictureUrl) {
		this.productPictureUrl = productPictureUrl;
	}

	public int getOrderProductionsCount() {
		return orderProductionsCount;
	}

	public void setOrderProductionsCount(int orderProductionsCount) {
		this.orderProductionsCount = orderProductionsCount;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
