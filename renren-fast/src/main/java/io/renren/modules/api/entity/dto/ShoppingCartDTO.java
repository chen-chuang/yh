package io.renren.modules.api.entity.dto;

import java.math.BigDecimal;

public class ShoppingCartDTO {
	
	private Long productId;

	private String productName;
	
	private BigDecimal productPrice;
	
	private String productPictureUrl;
	
	private String productVideoUrl;
	
	private Long productStoreNum;
	
	private int isCollected;
	
	private String productDetail;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

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

	public String getProductVideoUrl() {
		return productVideoUrl;
	}

	public void setProductVideoUrl(String productVideoUrl) {
		this.productVideoUrl = productVideoUrl;
	}

	public Long getProductStoreNum() {
		return productStoreNum;
	}

	public void setProductStoreNum(Long productStoreNum) {
		this.productStoreNum = productStoreNum;
	}

	public int getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(int isCollected) {
		this.isCollected = isCollected;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
}
