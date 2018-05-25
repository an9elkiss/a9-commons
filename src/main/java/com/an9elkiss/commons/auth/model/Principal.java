package com.an9elkiss.commons.auth.model;

import java.util.List;

public class Principal {

	private Subject subject;
	private List<Rights> rights;

	public Principal() {
	}

	public Principal(Subject subject, List<Rights> rights) {
		this.subject = subject;
		this.rights = rights;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<Rights> getRights() {
		return rights;
	}

	public void setRights(List<Rights> rights) {
		this.rights = rights;
	}

	public String getName() {
		return subject.getName();
	}


}
