package com.douniu.imshh.material.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.material.domain.MaterialIn;
import com.douniu.imshh.material.service.IMaterialInService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/mtlIn")
public class MaterialInAction {
	
	@Autowired
	private IMaterialInService service; 
	
	@Authorization("010202")
	@RequestMapping(value="/batchInStorage", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void batchInStorage(String materialStr, Date inDate, String supplier){
		Gson gson = new Gson();
		List<MaterialIn> materialIns =gson.fromJson(materialStr, new TypeToken<List<MaterialIn>>() {}.getType());
		for (MaterialIn materialIn : materialIns){
			materialIn.setInDate(inDate);
			materialIn.setSupplierId(supplier);
		}
		service.batchInStorage(materialIns);
	}
}
