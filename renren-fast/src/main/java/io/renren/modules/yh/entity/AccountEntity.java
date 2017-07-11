package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 资金账户表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-11 17:54:19
 */
public class AccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//区域经销商id
	private Long enterpriseId;
	//总金额
	private BigDecimal price;

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
	 * 设置：区域经销商id
	 */
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	/**
	 * 获取：区域经销商id
	 */
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	/**
	 * 设置：总金额
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：总金额
	 */
	public BigDecimal getPrice() {
		return price;
	}
}
