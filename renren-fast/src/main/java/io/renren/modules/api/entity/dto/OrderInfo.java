package io.renren.modules.api.entity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderInfo {
	
	private String orderID;
	
	private Date orderTime;
	
	private Long useIntegralCount;
	
	private BigDecimal orderAllPrice;
	
	private int orderType;
	
	private List<OrderProductions>  orderProductions;
	

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public Date getOrderSendTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Long getUseIntegralCount() {
		return useIntegralCount;
	}

	public void setUseIntegralCount(Long useIntegralCount) {
		this.useIntegralCount = useIntegralCount;
	}

	public BigDecimal getOrderAllPrice() {
		return orderAllPrice;
	}

	public void setOrderAllPrice(BigDecimal orderAllPrice) {
		this.orderAllPrice = orderAllPrice;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public List<OrderProductions> getOrderProductions() {
		return orderProductions;
	}

	public void setOrderProductions(List<OrderProductions> orderProductions) {
		this.orderProductions = orderProductions;
	}

}
