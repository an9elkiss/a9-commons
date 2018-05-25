package com.an9elkiss.commons.auth.model;

public class User implements Subject {

	private String name;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
