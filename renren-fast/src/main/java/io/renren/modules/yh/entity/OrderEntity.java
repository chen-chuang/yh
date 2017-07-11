package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 订单表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String orderId;
	//销售员id
	private Long userId;
	//订单生成时间
	private Date orderCreateTime;
	//使用积分数
	private Long userIntegralCount;
	//订单总价格
	private BigDecimal orderAllPrice;
	//订单状态(0待支付 1已支付 2代配送 3已完成)
	private Integer orderType;
	//下单地址ID
	private Integer downId;
	//下单地址
	private String orderAddress;
	//要求配送时间
	private Date orderSendTime;
	//详细地址
	private String orderDetailAddress;
	//买者电话
	private String receiverPhone;
	//买者姓名
	private String receiverName;
	//备注
	private String mark;
	//订单支付方式（1：支付宝，2：微信）
	private Integer orderPayType;
	//是否配送返点（已返点，未返点）
	private Integer isRebate;

	public Integer getIsRebate() {
		return isRebate;
	}
	public void setIsRebate(Integer isRebate) {
		this.isRebate = isRebate;
	}
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
	 * 设置：销售员id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：销售员id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：订单生成时间
	 */
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	/**
	 * 获取：订单生成时间
	 */
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	/**
	 * 设置：使用积分数
	 */
	public void setUserIntegralCount(Long userIntegralCount) {
		this.userIntegralCount = userIntegralCount;
	}
	/**
	 * 获取：使用积分数
	 */
	public Long getUserIntegralCount() {
		return userIntegralCount;
	}
	/**
	 * 设置：订单总价格
	 */
	public void setOrderAllPrice(BigDecimal orderAllPrice) {
		this.orderAllPrice = orderAllPrice;
	}
	/**
	 * 获取：订单总价格
	 */
	public BigDecimal getOrderAllPrice() {
		return orderAllPrice;
	}
	/**
	 * 设置：订单状态(0待支付 1已支付 2代配送 3已完成)
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	/**
	 * 获取：订单状态(0待支付 1已支付 2代配送 3已完成)
	 */
	public Integer getOrderType() {
		return orderType;
	}
	/**
	 * 设置：下单地址ID
	 */
	public void setDownId(Integer downId) {
		this.downId = downId;
	}
	/**
	 * 获取：下单地址ID
	 */
	public Integer getDownId() {
		return downId;
	}
	/**
	 * 设置：下单地址
	 */
	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
	/**
	 * 获取：下单地址
	 */
	public String getOrderAddress() {
		return orderAddress;
	}
	/**
	 * 设置：要求配送时间
	 */
	public void setOrderSendTime(Date orderSendTime) {
		this.orderSendTime = orderSendTime;
	}
	/**
	 * 获取：要求配送时间
	 */
	public Date getOrderSendTime() {
		return orderSendTime;
	}
	/**
	 * 设置：详细地址
	 */
	public void setOrderDetailAddress(String orderDetailAddress) {
		this.orderDetailAddress = orderDetailAddress;
	}
	/**
	 * 获取：详细地址
	 */
	public String getOrderDetailAddress() {
		return orderDetailAddress;
	}
	/**
	 * 设置：买者电话
	 */
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	/**
	 * 获取：买者电话
	 */
	public String getReceiverPhone() {
		return receiverPhone;
	}
	/**
	 * 设置：买者姓名
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	/**
	 * 获取：买者姓名
	 */
	public String getReceiverName() {
		return receiverName;
	}
	/**
	 * 设置：备注
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}
	/**
	 * 获取：备注
	 */
	public String getMark() {
		return mark;
	}
	/**
	 * 设置：订单支付方式（1：支付宝，2：微信）
	 */
	public void setOrderPayType(Integer orderPayType) {
		this.orderPayType = orderPayType;
	}
	/**
	 * 获取：订单支付方式（1：支付宝，2：微信）
	 */
	public Integer getOrderPayType() {
		return orderPayType;
	}
}
