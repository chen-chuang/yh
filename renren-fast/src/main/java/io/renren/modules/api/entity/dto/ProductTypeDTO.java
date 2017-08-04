package io.renren.modules.api.entity.dto;

public class ProductTypeDTO {
	
	private String catalog;
	
	private Integer categoryId;
	
	private String categoryImageUrl;
	
	private Integer showInHomepage;

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryImageUrl() {
		return categoryImageUrl;
	}

	public void setCategoryImageUrl(String categoryImageUrl) {
		this.categoryImageUrl = categoryImageUrl;
	}

	public Integer getShowInHomepage() {
		return showInHomepage;
	}

	public void setShowInHomepage(Integer showInHomepage) {
		this.showInHomepage = showInHomepage;
	}

}
