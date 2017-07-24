package io.renren.modules.api.entity.dto;

public class LoginDTO {
	
	private String userID;
	
	private int userPermission;
	
	private String userArea;
	
	private int areaID;
	
	private Long userIntegral;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(int userPermission) {
		this.userPermission = userPermission;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public Long getUserIntegral() {
		return userIntegral;
	}

	public void setUserIntegral(Long userIntegral) {
		this.userIntegral = userIntegral;
	}

}
