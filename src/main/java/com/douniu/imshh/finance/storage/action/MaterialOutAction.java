package com.douniu.imshh.finance.storage.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.storage.domain.MaterialOut;
import com.douniu.imshh.finance.storage.service.IMaterialOutService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MaterialOutAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("出库日期","outDate",0));  
		mapper.add(new ExcelBean("经手人","handMan",0));
		mapper.add(new ExcelBean("关联订单号","orderIdentify",0));  
		mapper.add(new ExcelBean("品名","materialName",0));  
		mapper.add(new ExcelBean("规格1","specification1",0));  
		mapper.add(new ExcelBean("规格2","specification2",0));		
		mapper.add(new ExcelBean("规格3","specification3",0));
		mapper.add(new ExcelBean("出库数量","outAmount",0));
		mapper.add(new ExcelBean("返回数量","returnAmount",0));	
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IMaterialOutService service;
	
	@RequestMapping(value="/findById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findById(MaterialOut out){
		MaterialOut oOut = service.getById(out.getId());
		Gson gson = new Gson();
        return gson.toJson(oOut);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(MaterialOut out){		
		service.save(out);
        return 1;
	}
	
	@RequestMapping(value="/return", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int returnMaterial(MaterialOut out){
		service.save(out);
		return 1;
	}
		
	
	@RequestMapping(value ="/loadmaterialout", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadMaterialOut(MaterialOut out){
		List<MaterialOut> res = service.query(out);
		int count = service.count(out);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
		
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}
	
    @RequestMapping(value = "exportmaterialout", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportMaterialOut(HttpServletRequest request,HttpServletResponse response,HttpSession session){  
    	response.reset();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssms");  
        String dateStr = sdf.format(new Date());  
         
        response.setHeader("Content-Disposition", "attachment;filename=" +dateStr+".xlsx");  
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
  
        Workbook workbook=null;  
        try {
        	String condition = request.getParameter("condition");
        	Date startDate = DateUtil.string2Date(request.getParameter("startDate"));
        	Date endDate = DateUtil.string2Date(request.getParameter("endDate"));
        	
        	MaterialOut out = new MaterialOut();
        	out.setCondition(condition);
        	out.setStartDate(startDate);
        	out.setEndDate(endDate);
        	
            List<MaterialOut> outs = service.queryNoPage(out);
        	workbook = POIExcelAdapter.toWorkBook(outs, mapper, MaterialOut.class); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        OutputStream output;  
        try {  
            output = response.getOutputStream();  
          
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
            bufferedOutPut.flush();  
            workbook.write(bufferedOutPut);  
            bufferedOutPut.close();  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}
