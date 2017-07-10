package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 积分兑现表（销售人员）
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class IntegrationcashEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//申请用户id
	private Long applyUserId;
	//兑现积分
	private Long integration;
	//提现金额
	private BigDecimal withdrawalamount;
	//提现申请时间
	private Date applyTime;
	//提现状态
	private Integer withdrawStatus;
	//操作时间（针对状态）
	private Date operateTime;
	//管理员id
	private Long userId;

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
	 * 设置：申请用户id
	 */
	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}
	/**
	 * 获取：申请用户id
	 */
	public Long getApplyUserId() {
		return applyUserId;
	}
	/**
	 * 设置：兑现积分
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}
	/**
	 * 获取：兑现积分
	 */
	public Long getIntegration() {
		return integration;
	}
	/**
	 * 设置：提现金额
	 */
	public void setWithdrawalamount(BigDecimal withdrawalamount) {
		this.withdrawalamount = withdrawalamount;
	}
	/**
	 * 获取：提现金额
	 */
	public BigDecimal getWithdrawalamount() {
		return withdrawalamount;
	}
	/**
	 * 设置：提现申请时间
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * 获取：提现申请时间
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	/**
	 * 设置：提现状态
	 */
	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}
	/**
	 * 获取：提现状态
	 */
	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}
	/**
	 * 设置：操作时间（针对状态）
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	/**
	 * 获取：操作时间（针对状态）
	 */
	public Date getOperateTime() {
		return operateTime;
	}
	/**
	 * 设置：管理员id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：管理员id
	 */
	public Long getUserId() {
		return userId;
	}
}
