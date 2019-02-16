package com.douniu.imshh.sys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.SystemFilter;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.service.IUserService;
import com.douniu.imshh.utils.EncryptUnit;
import com.douniu.imshh.utils.GsonUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/user")
public class UserAction {
	
	@Autowired
	private IUserService service;
	
	@Authorization("0601")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(SystemFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	/*	
	@RequestMapping(value ="/userProfile", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String userProfile(HttpSession httpSession){
		Object obj = httpSession.getAttribute("user");
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	@RequestMapping(value ="/editProfile", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void editProfile(User user, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpSession httpSession) throws Exception{
		if(!file.isEmpty()) {
            //上传文件路径
            String path = request.getSession().getServletContext().getRealPath("/fileupload/head/");
            //上传文件名
            String filename = ((User)httpSession.getAttribute("user")).getUserName()+"Head";//file.getOriginalFilename();
            File filepath = new File(path,filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) { 
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            user.setHead("/fileupload/head/"+filename);
        } else {
            user.setHead("");
        }
		service.updateProfile(user);
		httpSession.setAttribute("user", user);
	}
	

	@RequestMapping(value ="/loaduser", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadUser(User user){
		List<User> res = service.query(user);
		int count = service.count(user);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new Gson();
		return gson.toJson(pr);
	}
	
	@RequestMapping(value ="/loadRoses", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadRoses(){
		List<Role> roles = roleService.query();
		Gson gson = new Gson();
		return gson.toJson(roles);
	}*/
	
	@RequestMapping(value="/findById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findById(String id){
		User oUser = service.findById(id);
		Gson gson = new Gson();
        return gson.toJson(oUser);
	}
	
	@Authorization("0601")
	@RequestMapping(value="/newUser", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String newUser(User user, String rolestr){
		IDInjector.injector(user);
		String pwd = EncryptUnit.encrypt(user.getPassword());
		user.setPassword(pwd);
		
		if (!StringUtils.isEmpty(rolestr)){
			List<Role> roleList = new ArrayList<>();
			String[] roleIds = rolestr.split(",");
			for (String roleId : roleIds){
				Role role = new Role();
				role.setId(roleId);
				roleList.add(role);
			}
			user.setRoles(roleList);
		}
		service.add(user);
		return "success";
	}
	
	@Authorization("0601")
	@RequestMapping(value="/updateUser", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateUser(User user, String rolestr){
		String pwd = EncryptUnit.encrypt(user.getPassword());
		user.setPassword(pwd);
		if (!StringUtils.isEmpty(rolestr)){
			List<Role> roleList = new ArrayList<>();
			String[] roleIds = rolestr.split(",");
			for (String roleId : roleIds){
				Role role = new Role();
				role.setId(roleId);
				roleList.add(role);
			}
			user.setRoles(roleList);
		}
		service.update(user);
		return "success";
	}
		
	@Authorization("0601")
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id){
		service.remove(id);
		return "success";
	}
	
	@RequestMapping(value="/existUser", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String existUser(String id, String userName){
		Map<String, Boolean> result = new HashMap<>();
		result.put("valid", true);
		List<User> users = service.queryWithInvalid(new SystemFilter());
		for (User user : users){
			if (userName.equals(user.getUserName()) && !id.equals(user.getId())){
				result.put("valid", false);
				break;
			}
		}
				
		return GsonUtil.toJson(result);
	}
	
	@Authorization("0601")
	@RequestMapping(value="/resetPassword", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String resetPassword(String id){
		User user = service.findById(id);
		String pwd = EncryptUnit.encrypt("123456");
		user.setPassword(pwd);
		service.update(user);	
		return "success";
	}
	
	
	/*
	private List<Role> getFreeRoles(List<Role> allRole, List<Role> userRole){
		List<Role> freeRoles = new ArrayList<>();
		for (Role role : allRole){
			boolean hasRef = false;
			for (Role urole : userRole){
				if (role.getId().equals(urole.getId())){
					hasRef = true;
					break;
				}				
			}
			if (!hasRef){
				freeRoles.add(role);
			}
		}
		return freeRoles;
	}*/
}
