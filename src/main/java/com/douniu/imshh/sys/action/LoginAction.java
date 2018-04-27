package com.douniu.imshh.sys.action;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.domain.Menu;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.service.IAuthorityService;
import com.douniu.imshh.sys.service.IUserService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/login")
public class LoginAction {
	
	@Autowired
	private IUserService service;
	@Autowired
	private IAuthorityService authorityService;
	
	@RequestMapping("/login")
	@ResponseBody
	public int login(User user, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception{
		// 判断是否存在当前用户
		if (!service.existUserName(user.getUserName())) return 0;
		
		// 判断用户名密码是否正确
		if (service.verify(user)){
			httpSession.setAttribute("user", user);
			
			//保存用户权限到session
			User oUser = service.findByNmPwd(user);
			List<Authority> authorities = authorityService.queryByUser(oUser.getId());
			httpSession.setAttribute("userAuthority", authorities);
			
			//保存用户菜单到cookies
			List<Menu> menus =authorityService.queryMenuTreeByUser(oUser.getId());
			Gson gson = new Gson();
			String menuStr = gson.toJson(menus);
			menuStr = URLEncoder.encode(menuStr, "UTF-8");
			Cookie usermenu = null;
			if (request.getCookies() != null){
				for (Cookie cookie :request.getCookies()){
					if (cookie.getName().equals("userMenu")){
						usermenu = cookie;
						break;
					}
				}
			}
			
			if (usermenu != null){
				usermenu.setValue(menuStr);
				usermenu.setPath(request.getContextPath());
				usermenu.setSecure(false);
			}else{
				usermenu = new Cookie("userMenu", menuStr);
				usermenu.setPath(request.getContextPath());
				usermenu.setSecure(false);
			}
			response.addCookie(usermenu);
			
			return 1;
		}else{
			return -1;
		}
		
	}
	
	@RequestMapping(value="/logout", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int logout(User user, HttpServletRequest request, HttpSession httpSession){
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("userAuthority");
		for (Cookie cookie :request.getCookies()){
			cookie.setMaxAge(0);
		}
		return 1;
	}
	
}
