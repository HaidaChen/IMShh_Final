package com.douniu.imshh.sys.action;

import java.util.List;

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
			User oUser = service.findByNmPwd(user);
			httpSession.setAttribute("user", oUser);
			
			//保存用户权限到session
			List<Authority> authorities = authorityService.queryByUser(oUser.getId());
			httpSession.setAttribute("userAuthority", authorities);
			
			//保存用户菜单到session
			List<Menu> menus =authorityService.queryMenuTreeByUser(oUser.getId());
			Gson gson = new Gson();
			String menuStr = gson.toJson(menus);
			httpSession.setAttribute("userMenu", menuStr);
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
		httpSession.removeAttribute("userMenu");
		return 1;
	}
	
	@RequestMapping(value="/verifyPWD", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int verifyPWD(User user, HttpSession httpSession){
		Object oUser = httpSession.getAttribute("user");
		if (oUser != null && oUser instanceof User){
			User tUser = (User)oUser;
			if (tUser.getPassword().equals(user.getPassword())){
				return 1;
			}else{
				return -1;
			}
		}
		return 0;
	}
	
	@RequestMapping(value="/changePWD", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int changePWD(User user, HttpSession httpSession){
		Object oUser = httpSession.getAttribute("user");
		if (oUser instanceof User){
			User tUser = (User)oUser;
			tUser.setPassword(user.getPassword());
			service.update(tUser);
			return 1;
		}
		return 0;
	}
	
	@RequestMapping(value="/getUserMemu", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getUserMemu(HttpSession httpSession){
		Object obj = httpSession.getAttribute("userMenu");
		if (obj != null){
			return obj.toString();
		}else{
			return "";
		}
			
	}
	
	@RequestMapping(value="/userOnLine", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String userOnLine(HttpSession httSession){
		Object oUser = httSession.getAttribute("user");
		if (oUser != null){
			return "onLine";
		}else{
			return "offLine";
		}
	}
}
