package io.renren.modules.api.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

public class WithDrawDTO {
	
	private BigDecimal withdrawalAmount;
	
	private Date withdrawalTime;
	
	private String withdrawalStatus;

	public BigDecimal getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

	public Date getWithdrawalTime() {
		return withdrawalTime;
	}

	public void setWithdrawalTime(Date withdrawalTime) {
		this.withdrawalTime = withdrawalTime;
	}

	public String getWithdrawalStatus() {
		return withdrawalStatus;
	}

	public void setWithdrawalStatus(String withdrawalStatus) {
		this.withdrawalStatus = withdrawalStatus;
	}

}
