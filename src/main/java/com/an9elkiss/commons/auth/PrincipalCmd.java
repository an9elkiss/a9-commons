package com.an9elkiss.commons.auth;

import java.util.List;

public class PrincipalCmd {
	private SubjectCmd subject;
	private List<RightsCmd> rights;

	public SubjectCmd getSubject() {
		return subject;
	}

	public void setSubject(SubjectCmd subject) {
		this.subject = subject;
	}

	public List<RightsCmd> getRights() {
		return rights;
	}

	public void setRights(List<RightsCmd> rights) {
		this.rights = rights;
	}


}
