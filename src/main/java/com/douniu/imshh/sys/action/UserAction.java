package com.douniu.imshh.sys.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.domain.UserRole;
import com.douniu.imshh.sys.service.IRoleService;
import com.douniu.imshh.sys.service.IUserService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/user")
public class UserAction {
	
	@Autowired
	private IUserService service;
	@Autowired
	private IRoleService roleService;
	
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
	}
	
	@RequestMapping(value="/edit", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String edit(User user){
		User oUser = service.findById(user.getId());
		Gson gson = new Gson();
        return gson.toJson(oUser);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(User user, String[] roles_id){
		if (!"".equals(user.getId()) && user.getId() != null){
			service.update(user);
		}else{
			String id = System.currentTimeMillis() + "";
			user.setId(id);
			service.add(user);
		}
		
		if (roles_id != null){
			List<UserRole> userRoleList = new ArrayList<>();
			for (String roleId : roles_id){
				UserRole ur = new UserRole(user.getId(), roleId);
				userRoleList.add(ur);
			}
			service.deleteRoleRelation(user.getId());
			service.addRoleRelation(userRoleList);
		}
		return 1;
	}	
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.remove(id);
	}
	
	@RequestMapping("/existUser")
	@ResponseBody
	public String existUser(String id, String userName){
		Map<String, Boolean> result = new HashMap<>();
		
		if (!"".equals(id)){
			User user = service.findById(id);
			if (userName.equals(user.getUserName())){
				result.put("valid", true);
			}else{
				result.put("valid", false);
			}
		}else{
			if(service.existUserName(userName)){
				result.put("valid", false);
			}else{
				result.put("valid", true);
			}
		}
				
		Gson gson = new Gson();
		return gson.toJson(result);
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
