package com.todo1.api.model;

import java.io.Serializable;

public class Product extends AbstractModel<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
