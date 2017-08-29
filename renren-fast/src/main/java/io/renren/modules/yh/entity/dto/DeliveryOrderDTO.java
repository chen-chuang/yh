package io.renren.modules.yh.entity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DeliveryOrderDTO {
	
	private String orderId;
	
	private Date createTime;
	
	private String deliveryFaName;
	
	private String deliveryName;
	
	private String agency;
	
	private BigDecimal productAllPrice;
	
	private Long useIntegral;
	
	private BigDecimal productActualPrice;
	
	private String receiverName;
	
	private String orderDetailAddress;
	
	private String receiverPhone;
	
	private List<DeliveryOrderDetailDTO> products;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public BigDecimal getProductAllPrice() {
		return productAllPrice;
	}

	public void setProductAllPrice(BigDecimal productAllPrice) {
		this.productAllPrice = productAllPrice;
	}

	public Long getUseIntegral() {
		return useIntegral;
	}

	public void setUseIntegral(Long useIntegral) {
		this.useIntegral = useIntegral;
	}

	public BigDecimal getProductActualPrice() {
		return productActualPrice;
	}

	public void setProductActualPrice(BigDecimal productActualPrice) {
		this.productActualPrice = productActualPrice;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getOrderDetailAddress() {
		return orderDetailAddress;
	}

	public void setOrderDetailAddress(String orderDetailAddress) {
		this.orderDetailAddress = orderDetailAddress;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getDeliveryFaName() {
		return deliveryFaName;
	}

	public void setDeliveryFaName(String deliveryFaName) {
		this.deliveryFaName = deliveryFaName;
	}

	public List<DeliveryOrderDetailDTO> getProducts() {
		return products;
	}

	public void setProducts(List<DeliveryOrderDetailDTO> products) {
		this.products = products;
	}
	
	

}
