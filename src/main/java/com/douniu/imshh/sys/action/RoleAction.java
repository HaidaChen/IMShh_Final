package com.douniu.imshh.sys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.domain.JSTree;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.SystemFilter;
import com.douniu.imshh.sys.service.IAuthorityService;
import com.douniu.imshh.sys.service.IRoleService;
import com.douniu.imshh.utils.GsonUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/role")
public class RoleAction {
	@Autowired
	private IRoleService service;
	@Autowired
	private IAuthorityService authorityService;
	
	@Authorization("0602")
	@RequestMapping(value="/loadRoles", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadRoles(SystemFilter filter){
		List<Role> roles = service.query(filter);
		Gson gson = new Gson();
		return gson.toJson(roles);
	}
	
	@Authorization("0602")
	@RequestMapping(value="/findById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findById(String id){
		Role role = service.findById(id);
		return GsonUtil.toJson(role);
	}
	
	@Authorization("0602")
	@RequestMapping(value="/newRole", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String newRole(Role role, String authorityIds){
		service.newRole(role, authorityIds);
		return "success";
	}
	
	@Authorization("0602")
	@RequestMapping(value="/updateRole", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateRole(Role role, String authorityIds){
		service.updateRole(role, authorityIds);
		return "success";
	}
			
	@RequestMapping(value="/existRole", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String existRole(String id, String roleName){
		Map<String, Boolean> result = new HashMap<>();
		result.put("valid", true);
		List<Role> roles = service.query(new SystemFilter());
		for (Role role : roles){
			if (roleName.equals(role.getName()) && !id.equals(role.getId())){
				result.put("valid", false);
				break;
			}
		}
				
		return GsonUtil.toJson(result);
	}
	
	@Authorization("0602")
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}	
	
	@Authorization("0602")
	@RequestMapping(value ="/roleAuthority", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String roleAuthority(String roleId){
		List<Authority> auList = authorityService.query();
		List<Authority> roleAuthList = authorityService.queryByRole(roleId);
		JSTree root = new JSTree("0", "系统权限");
		root.getState().setOpened(true);
		loadChildTree(root, auList, roleAuthList);
		
		Gson gson = new Gson();
		return gson.toJson(root);
	}
	/*
	@RequestMapping(value ="/saveAuthority", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void saveAuthority(String roleId, String authorityIds){
		service.deleteAuthorityRelation(roleId);
		
		List<RoleAuthority> roleAuthorities = new ArrayList<>();
		String[] authorityIdArr = authorityIds.split(",");		
		for (String authorityId : authorityIdArr){
			RoleAuthority roleAuthority = new RoleAuthority(roleId, authorityId);
			roleAuthorities.add(roleAuthority);
		}
		service.addAuthorityRelation(roleAuthorities);
	}
	*/
	private void loadChildTree(JSTree parent, List<Authority> auList, List<Authority> roleAuthList){
		List<JSTree> childs = new ArrayList<JSTree>();
		for (Authority authority : auList){
			if (authority.getParentId().equals(parent.getId())){
				JSTree child = new JSTree(authority.getId(), authority.getName());
				if (containAuthority(child.getId(), roleAuthList)){
					child.checkNode();
				}else{
					child.uncheckNode();
				}
				childs.add(child);
				loadChildTree(child, auList, roleAuthList);
			}
		}
		parent.setChildren(childs);
	}
	
	private boolean containAuthority(String roleId, List<Authority> roleAuthList){
		for (Authority authority : roleAuthList){
			if (roleId.equals(authority.getId()))
				return true;
		}
		return false;
	}
}
