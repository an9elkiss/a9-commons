package com.an9elkiss.commons.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.an9elkiss.commons.auth.model.ApiRights;
import com.an9elkiss.commons.auth.model.MenuRights;
import com.an9elkiss.commons.auth.model.Principal;
import com.an9elkiss.commons.auth.model.Rights;
import com.an9elkiss.commons.auth.model.User;
import com.an9elkiss.commons.util.JsonUtils;

public class JsonFormater {

	public static Principal format(String json) {

		PrincipalCmd principalCmd = JsonUtils.parse(json, PrincipalCmd.class);

		User user = new User();
		BeanUtils.copyProperties(principalCmd.getSubject(), user);

		List<Rights> rights = new ArrayList<Rights>();
		for (RightsCmd rightsCmd : principalCmd.getRights()) {
			if (rightsCmd.getTypeId() == MenuRights.TYPE_ID) {
				MenuRights menuRights = new MenuRights();
				BeanUtils.copyProperties(rightsCmd, menuRights);
				rights.add(menuRights);
			} else if (rightsCmd.getTypeId() == ApiRights.TYPE_ID) {
				ApiRights apiRights = new ApiRights();
				BeanUtils.copyProperties(rightsCmd, apiRights);
				rights.add(apiRights);
			}
		}

		return new Principal(user, rights);
	}
}
