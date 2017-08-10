package io.renren.modules.yh.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;



/**
 * 企业信息表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class EnterpriseinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long enterpriseId;
	//企业名称
	@NotBlank(message="企业名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String enterpriseName;
	//企业图片
	private String enterpriseImageUrl;
	//企业地址
	private String enterpriseAddress;
	//手机
	private String enterprisePhone;
	//电话
	private String enterpriseTel;
	//联系人
	private String enterpriseContact;
	//简介
	private String enterpriseIntroduction;
	//经度
	private String enterpriseLongitude;
	//纬度
	private String enterpriseLatitude;
	//行政区域
	private String enterpriseAreaId;
	
	private String enterpriseAreaName;
	//类型（1：生产厂家，2：经销商）
	@NotNull(message="企业类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer enterpriseType;

	/**
	 * 设置：
	 */
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	/**
	 * 获取：
	 */
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	/**
	 * 设置：企业名称
	 */
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	/**
	 * 获取：企业名称
	 */
	public String getEnterpriseName() {
		return enterpriseName;
	}
	/**
	 * 设置：企业图片
	 */
	public void setEnterpriseImageUrl(String enterpriseImageUrl) {
		this.enterpriseImageUrl = enterpriseImageUrl;
	}
	/**
	 * 获取：企业图片
	 */
	public String getEnterpriseImageUrl() {
		return enterpriseImageUrl;
	}
	/**
	 * 设置：企业地址
	 */
	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}
	/**
	 * 获取：企业地址
	 */
	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}
	/**
	 * 设置：手机
	 */
	public void setEnterprisePhone(String enterprisePhone) {
		this.enterprisePhone = enterprisePhone;
	}
	/**
	 * 获取：手机
	 */
	public String getEnterprisePhone() {
		return enterprisePhone;
	}
	/**
	 * 设置：电话
	 */
	public void setEnterpriseTel(String enterpriseTel) {
		this.enterpriseTel = enterpriseTel;
	}
	/**
	 * 获取：电话
	 */
	public String getEnterpriseTel() {
		return enterpriseTel;
	}
	/**
	 * 设置：联系人
	 */
	public void setEnterpriseContact(String enterpriseContact) {
		this.enterpriseContact = enterpriseContact;
	}
	/**
	 * 获取：联系人
	 */
	public String getEnterpriseContact() {
		return enterpriseContact;
	}
	/**
	 * 设置：简介
	 */
	public void setEnterpriseIntroduction(String enterpriseIntroduction) {
		this.enterpriseIntroduction = enterpriseIntroduction;
	}
	/**
	 * 获取：简介
	 */
	public String getEnterpriseIntroduction() {
		return enterpriseIntroduction;
	}
	/**
	 * 设置：经度
	 */
	public void setEnterpriseLongitude(String enterpriseLongitude) {
		this.enterpriseLongitude = enterpriseLongitude;
	}
	/**
	 * 获取：经度
	 */
	public String getEnterpriseLongitude() {
		return enterpriseLongitude;
	}
	/**
	 * 设置：纬度
	 */
	public void setEnterpriseLatitude(String enterpriseLatitude) {
		this.enterpriseLatitude = enterpriseLatitude;
	}
	/**
	 * 获取：纬度
	 */
	public String getEnterpriseLatitude() {
		return enterpriseLatitude;
	}
	/**
	 * 设置：行政区域
	 */
	public void setEnterpriseAreaId(String enterpriseAreaId) {
		this.enterpriseAreaId = enterpriseAreaId;
	}
	/**
	 * 获取：行政区域
	 */
	public String getEnterpriseAreaId() {
		return enterpriseAreaId;
	}
	/**
	 * 设置：类型（1：生产厂家，2：经销商）
	 */
	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	/**
	 * 获取：类型（1：生产厂家，2：经销商）
	 */
	public Integer getEnterpriseType() {
		return enterpriseType;
	}
	public String getEnterpriseAreaName() {
		return enterpriseAreaName;
	}
	public void setEnterpriseAreaName(String enterpriseAreaName) {
		this.enterpriseAreaName = enterpriseAreaName;
	}
}
