package com.an9elkiss.commons.auth.model;

public class User implements Subject {

	private String name;
	
	private Integer id;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	
}
