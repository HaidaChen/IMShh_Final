package com.douniu.imshh.sys.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView login(User user, HttpSession httpSession){
		ModelAndView mav = new ModelAndView();
		Boolean verify = service.verify(user);		
		if (verify){
			httpSession.setAttribute("user", user);
			
			User oUser = service.findByNmPwd(user);
			List<Authority> authorities = authorityService.queryByUser(oUser.getId());
			httpSession.setAttribute("userAuthority", authorities);
			mav.setViewName("../index");
		}else{
			mav.setViewName("../login");
			mav.addObject("tip", "用户名或密码不正确，请重新输入");
		}
		return mav;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(User user, HttpSession httpSession){
		ModelAndView mav = new ModelAndView();
		httpSession.removeAttribute("user");
		mav.setViewName("../login");
		return mav;
	}
}
