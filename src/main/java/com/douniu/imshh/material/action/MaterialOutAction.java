package com.douniu.imshh.material.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.material.domain.MaterialOut;
import com.douniu.imshh.material.service.IMaterialOutService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/mtlOut")
public class MaterialOutAction {
	@Autowired
	private IMaterialOutService service; 
	
	@Authorization("010302")
	@RequestMapping(value="/batchOutStorage", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void batchOutStorage(String materialStr, Date outDate){
		Gson gson = new Gson();
		List<MaterialOut> materialOuts =gson.fromJson(materialStr, new TypeToken<List<MaterialOut>>() {}.getType());
		for (MaterialOut materialOut : materialOuts){
			materialOut.setOutDate(outDate);
		}
		service.batchOutStorage(materialOuts);
	}
}
