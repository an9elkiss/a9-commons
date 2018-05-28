package com.an9elkiss.commons.auth;

import java.util.ArrayList;

import com.an9elkiss.commons.auth.model.Principal;
import com.an9elkiss.commons.auth.model.Rights;
import com.an9elkiss.commons.auth.model.User;

public class AppContext {

	private static final ThreadLocal<Principal> localPrincial = new ThreadLocal<Principal>();
	private static final Principal emptyPrincial;

	static {
		emptyPrincial = new Principal();

		User user = new User();
		user.setName("---");
		emptyPrincial.setSubject(user);

		emptyPrincial.setRights(new ArrayList<Rights>());
	}

	public static Principal getPrincipal() {

		Principal principal = localPrincial.get();
		if (principal == null) {
			return emptyPrincial;
		} else {
			return principal;
		}
	}

	public static void setPrincipal(Principal principal) {
		localPrincial.set(principal);
	}

	public static void cleanPrincipal() {
		localPrincial.set(emptyPrincial);
	}

}
