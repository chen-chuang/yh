package io.renren.modules.api.entity.dto;

import java.util.Date;

public class UserInfo {
	
	private String orderAddress;
	
	private String orderDetailAddress;
	
	private Date orderSendTime;
	
	private String receiverPhone;
	
	private String receiverName;
	
	private String mark;

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getOrderDetailAddress() {
		return orderDetailAddress;
	}

	public void setOrderDetailAddress(String orderDetailAddress) {
		this.orderDetailAddress = orderDetailAddress;
	}

	public Date getOrderSendTime() {
		return orderSendTime;
	}

	public void setOrderSendTime(Date orderSendTime) {
		this.orderSendTime = orderSendTime;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
