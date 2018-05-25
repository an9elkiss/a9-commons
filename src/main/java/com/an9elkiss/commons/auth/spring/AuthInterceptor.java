package com.an9elkiss.commons.auth.spring;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.an9elkiss.commons.auth.JsonFormater;
import com.an9elkiss.commons.auth.model.Principal;
import com.an9elkiss.commons.auth.model.Rights;
import com.an9elkiss.commons.constant.RedisKeyPrefix;
import com.an9elkiss.commons.util.spring.RedisUtils;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// ��handlerǿתΪHandlerMethod, ǰ���Ѿ�֤ʵ���handler����HandlerMethod
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// �ӷ����������л�ȡ��Ҫ���õķ���
		Method method = handlerMethod.getMethod();
		// ��ȡ�������ϵ�Accessע��
		Access access = method.getAnnotation(Access.class);
		if (access == null) {
			// ���ע��Ϊnull, ˵������Ҫ����, ֱ�ӷŹ�
			return true;
		}

		// Set<String> authSet = new HashSet<String>();
		// if (access.authorities().length > 0) {
		// // ���Ȩ�����ò�Ϊ��, ��ȡ������ֵ
		// String[] authorities = access.authorities();
		//
		// for (String authority : authorities) {
		// // ��Ȩ�޼���һ��set������
		// authSet.add(authority);
		// }
		// }

		String token = request.getParameter("token");
		if (StringUtils.isBlank(token)) {
			return accessDeny(request, response);
		}

		// get Principal
		String json = redisUtils.getString(RedisKeyPrefix.SESSION + token);
		if (StringUtils.isBlank(json)) {
			return accessDeny(request, response);
		}
		Principal principal = JsonFormater.format(json);
		for (Rights r : principal.getRights()) {
			if (r.getCode().equals(access.value())) {
				return true;
			}
		}

		// ����֮��Ӧ�÷��ع������, ����û������
		return accessDeny(request, response);
	}

	private boolean accessDeny(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/access-deny").forward(request, response);
		return false;
	}

}
