package com.an9elkiss.commons.auth;

import com.an9elkiss.commons.auth.model.Principal;
import com.an9elkiss.commons.auth.model.User;

public class AppContext {

	// TODO
	public static Principal getPrincipal() {
		Principal principal = new Principal();
		User user = new User();
		user.setName("rz");
		principal.setSubject(user);

		return principal;
	}

}
