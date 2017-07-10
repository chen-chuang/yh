package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 配置表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:49:26
 */
public class ConfigtableEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//配置key
	private String configKey;
	//配置名称
	private String configName;
	//配置值
	private String configValue;
	//配置人id
	private Long configUserId;
	//配置人名称
	private String configUserName;
	//配置人区域id
	private Integer configRegionId;
	//配置人区域名
	private String configReginName;
	//配置时间
	private Date configCreateTime;

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
	 * 设置：配置key
	 */
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	/**
	 * 获取：配置key
	 */
	public String getConfigKey() {
		return configKey;
	}
	/**
	 * 设置：配置名称
	 */
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	/**
	 * 获取：配置名称
	 */
	public String getConfigName() {
		return configName;
	}
	/**
	 * 设置：配置值
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	/**
	 * 获取：配置值
	 */
	public String getConfigValue() {
		return configValue;
	}
	/**
	 * 设置：配置人id
	 */
	public void setConfigUserId(Long configUserId) {
		this.configUserId = configUserId;
	}
	/**
	 * 获取：配置人id
	 */
	public Long getConfigUserId() {
		return configUserId;
	}
	/**
	 * 设置：配置人名称
	 */
	public void setConfigUserName(String configUserName) {
		this.configUserName = configUserName;
	}
	/**
	 * 获取：配置人名称
	 */
	public String getConfigUserName() {
		return configUserName;
	}
	/**
	 * 设置：配置人区域id
	 */
	public void setConfigRegionId(Integer configRegionId) {
		this.configRegionId = configRegionId;
	}
	/**
	 * 获取：配置人区域id
	 */
	public Integer getConfigRegionId() {
		return configRegionId;
	}
	/**
	 * 设置：配置人区域名
	 */
	public void setConfigReginName(String configReginName) {
		this.configReginName = configReginName;
	}
	/**
	 * 获取：配置人区域名
	 */
	public String getConfigReginName() {
		return configReginName;
	}
	/**
	 * 设置：配置时间
	 */
	public void setConfigCreateTime(Date configCreateTime) {
		this.configCreateTime = configCreateTime;
	}
	/**
	 * 获取：配置时间
	 */
	public Date getConfigCreateTime() {
		return configCreateTime;
	}
}
