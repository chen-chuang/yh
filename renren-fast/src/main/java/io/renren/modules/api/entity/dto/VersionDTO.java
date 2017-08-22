package io.renren.modules.api.entity.dto;

import java.util.Date;

public class VersionDTO {
	
	private String appUrl;
	
	private String appVersion;
	
	private String appVersionMsg;
	
	private Date appVersionDate;

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppVersionMsg() {
		return appVersionMsg;
	}

	public void setAppVersionMsg(String appVersionMsg) {
		this.appVersionMsg = appVersionMsg;
	}

	public Date getAppVersionDate() {
		return appVersionDate;
	}

	public void setAppVersionDate(Date appVersionDate) {
		this.appVersionDate = appVersionDate;
	}

}
