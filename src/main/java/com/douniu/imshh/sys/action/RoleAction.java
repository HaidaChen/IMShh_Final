package com.douniu.imshh.sys.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.domain.JSTree;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.RoleAuthority;
import com.douniu.imshh.sys.service.IAuthorityService;
import com.douniu.imshh.sys.service.IRoleService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/role")
public class RoleAction {
	@Autowired
	private IRoleService service;
	@Autowired
	private IAuthorityService authorityService;
	
	@RequestMapping("/main")
    public ModelAndView enter(){
        ModelAndView mav = new ModelAndView();
        List<Role> roles = service.query();
        mav.addObject("roles", roles);
        mav.setViewName("/sys/roleOverview");
        return mav;
    }
	
	@RequestMapping("/edit")
	public ModelAndView edit(Role role){
		ModelAndView mav = new ModelAndView();
		if (!"".equals(role.getId()) && role.getId() != null){
			Role oRole = service.findById(role.getId());
			mav.addObject("role", oRole);
		}
        mav.setViewName("/sys/roleEdit");
        return mav;
	}
	
	@RequestMapping("/save")
	public ModelAndView save(Role role){
		service.add(role);
		return enter();
	}	
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}	
	
	@RequestMapping(value ="/allAuthority", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryAllAuthority(String roleId){
		List<Authority> auList = authorityService.query();
		List<Authority> roleAuthList = authorityService.queryByRole(roleId);
		JSTree root = new JSTree("0", "系统权限");
		root.getState().setOpened(true);
		loadChildTree(root, auList, roleAuthList);
		
		Gson gson = new Gson();
		return gson.toJson(root);
	}
	
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
