package com.douniu.imshh.finance.storage.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.storage.domain.MaterialIn;
import com.douniu.imshh.finance.storage.service.IMaterialInService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.ExcelUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/materialin")
public class MaterialInAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("接收日期","receiveDate",0));  
		mapper.add(new ExcelBean("关联订单号","orderIdentify",0));  
		mapper.add(new ExcelBean("供应商","supplierName",0));   
		mapper.add(new ExcelBean("品名","materialName",0));  
		mapper.add(new ExcelBean("规格1","specification1",0));  
		mapper.add(new ExcelBean("规格2","specification2",0));		
		mapper.add(new ExcelBean("规格3","specification3",0));
		mapper.add(new ExcelBean("计量单位","unit",0));
		mapper.add(new ExcelBean("计量数","formula",0));		
		mapper.add(new ExcelBean("单价","unitPrice",0));
		mapper.add(new ExcelBean("数量","amount",0));
		mapper.add(new ExcelBean("合计","totlemnt",0));
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IMaterialInService service;
	
	@RequestMapping(value="/findById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String edit(MaterialIn reception){
		MaterialIn oReception = service.getById(reception.getId());
		Gson gson = new Gson();
        return gson.toJson(oReception);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(MaterialIn reception){
		service.save(reception);
        return 1;
	}
	
	@RequestMapping(value ="/loadmaterialin", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadMaterialIn(MaterialIn reception){
		List<MaterialIn> res = service.query(reception);
		int count = service.count(reception);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
	
	
	@RequestMapping(value ="/loadbysupplier", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadBySupplier(MaterialIn reception){
		List<MaterialIn> res = service.queryBySupplier(reception);
		int count = service.countBySupplier(reception);
		
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
	
	
    @ResponseBody  
    @RequestMapping(value="importmaterialin",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  importMaterialIn(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在");  
        }  
          
        in = file.getInputStream();  
        List<List<Object>>  data = ExcelUtil.parseExcel(in, file.getOriginalFilename());
        List<MaterialIn> reception = POIExcelAdapter.toDomainList(data, mapper, MaterialIn.class);
        service.batchAdd(reception);
    }  
    
    @RequestMapping(value = "exportmaterialin", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportMaterialIn(HttpServletRequest request,HttpServletResponse response,HttpSession session){  
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
        	
        	MaterialIn reception = new MaterialIn();
        	reception.setCondition(condition);
        	reception.setStartDate(startDate);
        	reception.setEndDate(endDate);
        	
            List<MaterialIn> receptions = service.queryNoPage(reception);
        	workbook = POIExcelAdapter.toWorkBook(receptions, mapper, MaterialIn.class); 
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
