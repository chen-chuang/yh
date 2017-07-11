package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 订单积分表（销售人员、配送员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class OrderintegrationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//用户id
	private Long userId;
	//订单id
	private String orderId;
	//订单金额
	private BigDecimal orderSumPrice;
	//生成积分
	private Long integration;
	//价格积分类型（1：配送积分?，2：销售积分）
	private Integer priceIntegrationType;
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
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：订单id
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：订单金额
	 */
	public void setOrderSumPrice(BigDecimal orderSumPrice) {
		this.orderSumPrice = orderSumPrice;
	}
	/**
	 * 获取：订单金额
	 */
	public BigDecimal getOrderSumPrice() {
		return orderSumPrice;
	}
	/**
	 * 设置：生成积分
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}
	/**
	 * 获取：生成积分
	 */
	public Long getIntegration() {
		return integration;
	}
	/**
	 * 设置：价格积分类型（1：配送积分?，2：销售积分）
	 */
	public void setPriceIntegrationType(Integer priceIntegrationType) {
		this.priceIntegrationType = priceIntegrationType;
	}
	/**
	 * 获取：价格积分类型（1：配送积分?，2：销售积分）
	 */
	public Integer getPriceIntegrationType() {
		return priceIntegrationType;
	}
}
