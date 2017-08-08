package io.renren.modules.api.entity.dto;


public class EnterpriseProductions {
	
	private String productName;
	
	private String productDetail;
	
	private String productPictureUrl;
	
	private String productVideoUrl;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductPictureUrl() {
		return productPictureUrl;
	}

	public void setProductPictureUrl(String productPictureUrl) {
		this.productPictureUrl = productPictureUrl;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

	public String getProductVideoUrl() {
		return productVideoUrl;
	}

	public void setProductVideoUrl(String productVideoUrl) {
		this.productVideoUrl = productVideoUrl;
	}


}
