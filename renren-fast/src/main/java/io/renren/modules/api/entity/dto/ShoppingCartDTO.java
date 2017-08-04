package io.renren.modules.api.entity.dto;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartDTO {
	
	private List<ShoppingCartDetailDTO> data;
	
	private String catalog;
	
	private int categoryId;
	
	private String categoryImageUrl;


	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryImageUrl() {
		return categoryImageUrl;
	}

	public void setCategoryImageUrl(String categoryImageUrl) {
		this.categoryImageUrl = categoryImageUrl;
	}

	public List<ShoppingCartDetailDTO> getData() {
		return data;
	}

	public void setData(List<ShoppingCartDetailDTO> data) {
		this.data = data;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
}
