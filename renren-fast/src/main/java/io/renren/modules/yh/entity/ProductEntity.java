package io.renren.modules.yh.entity;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 产品表
 * 
 * @author achuang
 * @email 317402777@qq.com
 * @date 2017-07-10 16:34:30
 */
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//产品ID
	private Long productId;
	//产品名称
	private String productName;
	//图片地址
	private String productPictureUrl;
	//简介
	private String productDetail;
	//产品类型：1.烟花 2.爆竹 3.套餐 4.小烟花
	private Integer productType;
	
	private String productTypeName;
	
	//视频地址
	private String productVideoUrl;
	//库存
	private Long productNum;
	//批发价
	private BigDecimal productTradePrice;
	//零售价
	private BigDecimal productRetailPrice;
	//企业ID
	private Long enterpriseId;
	
	private String enterpriseName;

	//是否热销（1：热销，2：不热销）
	private Integer isHot;
	//录入类型（1：管理员，2：区域代理）
	private Integer enterType;
	//录入人
	private String enterName;
	
	private Long enterPersonId;

	/**
	 * 设置：产品ID
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品ID
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * 设置：产品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：产品名称
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置：图片地址
	 */
	public void setProductPictureUrl(String productPictureUrl) {
		this.productPictureUrl = productPictureUrl;
	}
	/**
	 * 获取：图片地址
	 */
	public String getProductPictureUrl() {
		return productPictureUrl;
	}
	/**
	 * 设置：简介
	 */
	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
	/**
	 * 获取：简介
	 */
	public String getProductDetail() {
		return productDetail;
	}
	/**
	 * 设置：产品类型：1.烟花 2.爆竹 3.套餐 4.小烟花
	 */
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	/**
	 * 获取：产品类型：1.烟花 2.爆竹 3.套餐 4.小烟花
	 */
	public Integer getProductType() {
		return productType;
	}
	/**
	 * 设置：视频地址
	 */
	public void setProductVideoUrl(String productVideoUrl) {
		this.productVideoUrl = productVideoUrl;
	}
	/**
	 * 获取：视频地址
	 */
	public String getProductVideoUrl() {
		return productVideoUrl;
	}
	/**
	 * 设置：库存
	 */
	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}
	/**
	 * 获取：库存
	 */
	public Long getProductNum() {
		return productNum;
	}
	/**
	 * 设置：批发价
	 */
	public void setProductTradePrice(BigDecimal productTradePrice) {
		this.productTradePrice = productTradePrice;
	}
	/**
	 * 获取：批发价
	 */
	public BigDecimal getProductTradePrice() {
		return productTradePrice;
	}
	/**
	 * 设置：零售价
	 */
	public void setProductRetailPrice(BigDecimal productRetailPrice) {
		this.productRetailPrice = productRetailPrice;
	}
	/**
	 * 获取：零售价
	 */
	public BigDecimal getProductRetailPrice() {
		return productRetailPrice;
	}
	/**
	 * 设置：企业ID
	 */
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	/**
	 * 获取：企业ID
	 */
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	/**
	 * 设置：是否热销（1：热销，2：不热销）
	 */
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
	/**
	 * 获取：是否热销（1：热销，2：不热销）
	 */
	public Integer getIsHot() {
		return isHot;
	}
	/**
	 * 设置：录入类型（1：管理员，2：区域代理）
	 */
	public void setEnterType(Integer enterType) {
		this.enterType = enterType;
	}
	/**
	 * 获取：录入类型（1：管理员，2：区域代理）
	 */
	public Integer getEnterType() {
		return enterType;
	}
	
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getEnterName() {
		return enterName;
	}
	public void setEnterName(String enterName) {
		this.enterName = enterName;
	}
	public Long getEnterPersonId() {
		return enterPersonId;
	}
	public void setEnterPersonId(Long enterPersonId) {
		this.enterPersonId = enterPersonId;
	}
}
