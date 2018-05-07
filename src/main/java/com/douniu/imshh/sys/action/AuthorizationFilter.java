package com.douniu.imshh.sys.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douniu.imshh.sys.domain.Authority;

public class AuthorizationFilter implements Filter{
	private FilterConfig config;
	
	@Override
	public void destroy() {
		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest)request;
		HttpServletResponse hresponse = (HttpServletResponse)response;
		
		String loginStrings = config.getInitParameter("loginStrings");
		String includeStrings = config.getInitParameter("includeStrings");
		
		String[] loginUrls = loginStrings.split(";");
		String[] filterFlag = includeStrings.split(";");
		
		if (!this.isContains(hrequest.getRequestURI(), filterFlag)) {// 只对指定过滤参数后缀进行过滤
            chain.doFilter(request, response);
            return;
        }

        if (this.isContains(hrequest.getRequestURI(), loginUrls)) {// 对登录页面不进行过滤
            chain.doFilter(request, response);
            return;
        }
        
        if (hrequest.getSession().getAttribute("user") == null){
        	hresponse.sendRedirect(hrequest.getContextPath()+"/login.html");
        	return;
        }else{
        	List<Authority> authorities = (List<Authority>) hrequest.getSession().getAttribute("userAuthority");
        	String url = hrequest.getRequestURL().toString();
        	boolean hasAuth = false;
        	for (Authority authority : authorities){
        		if (authority.getAction().equals(""))
        			continue;
        		if (url.indexOf(authority.getAction()) > -1){
        			hasAuth = true;
        			break;
        		}
        	}
        	if (hasAuth){
        		chain.doFilter(request, response);
        	}else{
        		hresponse.sendRedirect("/IMShh/login.jsp");
        		hrequest.setAttribute("tip", "权限不够");
        	}
            return;
        }
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public boolean isContains(String container, String[] regx) {
        boolean result = false;

        for (int i = 0; i < regx.length; i++) {
            if (container.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return result;
    }
}
