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

import com.an9elkiss.commons.auth.AppContext;
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

		AppContext.cleanPrincipal();

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 从方法处理器中获取出要调用的方法
		Method method = handlerMethod.getMethod();
		// 获取出方法上的Access注解
		Access access = method.getAnnotation(Access.class);
		if (access == null) {
			// 如果注解为null, 说明不需要拦截, 直接放过
			return true;
		}

		// Set<String> authSet = new HashSet<String>();
		// if (access.authorities().length > 0) {
		// // 如果权限配置不为空, 则取出配置值
		// String[] authorities = access.authorities();
		//
		// for (String authority : authorities) {
		// // 将权限加入一个set集合中
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
		AppContext.setPrincipal(principal);
		for (Rights r : principal.getRights()) {
			if (r.getCode().equals(access.value())) {
				return true;
			}
		}

		// 拦截之后应该返回公共结果, 这里没做处理
		return accessDeny(request, response);
	}

	private boolean accessDeny(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/access-deny").forward(request, response);
		return false;
	}

}
