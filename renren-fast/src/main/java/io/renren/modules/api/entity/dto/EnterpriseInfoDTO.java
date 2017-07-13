package io.renren.modules.api.entity.dto;

public class EnterpriseInfoDTO {
	
	private String enterpriseImageUrl;
	
	private String enterpriseName;
	
	private String enterprisePhone;
	
	private String enterpriseAddress;
	
	private Long enterpriseID;

	public String getEnterpriseImageUrl() {
		return enterpriseImageUrl;
	}

	public void setEnterpriseImageUrl(String enterpriseImageUrl) {
		this.enterpriseImageUrl = enterpriseImageUrl;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getEnterprisePhone() {
		return enterprisePhone;
	}

	public void setEnterprisePhone(String enterprisePhone) {
		this.enterprisePhone = enterprisePhone;
	}

	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}

	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}

	public Long getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(Long enterpriseID) {
		this.enterpriseID = enterpriseID;
	}

}
