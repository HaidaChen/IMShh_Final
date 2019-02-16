package com.douniu.imshh.sys.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.NoPermissionException;
import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.service.IParameterService;

public class AuthorizationIntercepter implements HandlerInterceptor{
	@Autowired
	private IParameterService parameterService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (parameterService.getBoolean("debug")) return true;
		
		
		if (handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			Authorization a = hm.getMethodAnnotation(Authorization.class);
			if (a != null){
				String authCode = a.value();
				List<Authority> authorities = (List<Authority>) request.getSession().getAttribute("userAuthority");
				if (authorities != null){
					for (Authority authority : authorities){
						if (authority.getId().equals(authCode))
							return true;
					}
				}else{
					StringBuilder sb = new StringBuilder();
					String port = "",contextPath="";
					if(request.getServerPort()!=80){
						port = ":" + request.getServerPort();
					}
					if(StringUtils.isEmpty(request.getContextPath().replace("/", ""))){
						contextPath = request.getContextPath();
					}
					sb.append(request.getScheme()).append("://").append(request.getServerName()).append(port).append(contextPath);
					
					String loginPath =  sb.toString(); 
					String type = request.getHeader("X-Requested-With")==null?"":request.getHeader("X-Requested-With");
					if ("XMLHttpRequest".equals(type)) {
	                    //response.setHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向  
	                    //response.setHeader("CONTEXTPATH", loginPath);//重定向地址  
	                    //response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                    response.getWriter().write("has no permission");
	                    return false;
	                }else{//如果不是ajax请求，则直接重定向
	                    response.sendRedirect(loginPath);  
	                    return false;  
	                } 
				}
			}
		}
		
		return true;
	}

}
