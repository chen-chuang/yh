package io.renren.modules.yh.entity.dto;

import java.math.BigDecimal;

public class DeliveryOrderDetailDTO {
	
	private String productId;
	
	private String productName;
	
	private Long productNum;
	
	private BigDecimal productSumPrice;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductNum() {
		return productNum;
	}

	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}

	public BigDecimal getProductSumPrice() {
		return productSumPrice;
	}

	public void setProductSumPrice(BigDecimal productSumPrice) {
		this.productSumPrice = productSumPrice;
	}

}
