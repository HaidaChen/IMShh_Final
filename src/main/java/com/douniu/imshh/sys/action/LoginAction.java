package com.douniu.imshh.sys.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.service.IAuthorityService;
import com.douniu.imshh.sys.service.IUserService;

@Controller
@RequestMapping("/login")
public class LoginAction {
	
	@Autowired
	private IUserService service;
	@Autowired
	private IAuthorityService authorityService;
	
	@RequestMapping("/login")
	@ResponseBody
	public int login(User user, HttpSession httpSession){
		// 判断是否存在当前用户
		if (!service.existUserName(user.getUserName())) return 0;
		
		// 判断用户名密码是否正确
		if (service.verify(user)){
			httpSession.setAttribute("user", user);
			
			//保存用户权限到session
			User oUser = service.findByNmPwd(user);
			List<Authority> authorities = authorityService.queryByUser(oUser.getId());
			httpSession.setAttribute("userAuthority", authorities);
			
			return 1;
		}else{
			return -1;
		}
		
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public int logout(User user, HttpSession httpSession){
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("userAuthority");
		return 1;
	}
	
}
