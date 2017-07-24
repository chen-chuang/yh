package io.renren.modules.api.entity.dto;

public class CollectionDTO {
	
	private Long productId;
	
	private String productName;
	
	private String productDetail;
	
	private String productPictureUrl;
	
	private String productType;

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

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

	public String getProductPictureUrl() {
		return productPictureUrl;
	}

	public void setProductPictureUrl(String productPictureUrl) {
		this.productPictureUrl = productPictureUrl;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}
