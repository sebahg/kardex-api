package com.todo1.api.model;

import java.io.Serializable;

public class Stock extends AbstractModel<String> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String productId;
	private Long count;
	
	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}
