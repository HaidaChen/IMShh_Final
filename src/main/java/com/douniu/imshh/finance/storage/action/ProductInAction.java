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
import com.douniu.imshh.finance.storage.domain.ProductIn;
import com.douniu.imshh.finance.storage.service.IProductInService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.ExcelUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProductInAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("入库日期","storageDate",0));  
		mapper.add(new ExcelBean("关联订单号","orderIdentify",0)); 
		mapper.add(new ExcelBean("货号","pdtNo",0));  
		mapper.add(new ExcelBean("含量","content",0));   
		mapper.add(new ExcelBean("数量","amount",0));  
		mapper.add(new ExcelBean("备注","remark",0));
	}
	
	@Autowired
	private IProductInService service;
	
	
	@RequestMapping(value="/findbyid", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findById(String id){
		ProductIn oStorage = service.getById(id);
		Gson gson = new Gson();
        return gson.toJson(oStorage);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(ProductIn storage){
		service.save(storage);
        return 1;
	}
	
	@RequestMapping(value ="/loadproductin", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadStorage(ProductIn storage){
		List<ProductIn> res = service.queryDetail(storage);
		int count = service.count(storage);
		
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
	
	
    @RequestMapping(value="importproductin",method={RequestMethod.GET,RequestMethod.POST})  
    @ResponseBody  
    public  void  importProductIn(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在");  
        }  
          
        in = file.getInputStream();  
        List<List<Object>>  data = ExcelUtil.parseExcel(in, file.getOriginalFilename());
        List<ProductIn> storages = POIExcelAdapter.toDomainList(data, mapper, ProductIn.class);
        service.batchAdd(storages);
    }  
    
    @RequestMapping(value = "exportproductin", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportProductIn(HttpServletRequest request,HttpServletResponse response,HttpSession session){  
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
        	
        	ProductIn storage = new ProductIn();
        	storage.setCondition(condition);
        	storage.setStartDate(startDate);
        	storage.setEndDate(endDate);
        	
            List<ProductIn> storages = service.queryDetailNoPage(storage);
        	workbook = POIExcelAdapter.toWorkBook(storages, mapper, ProductIn.class); 
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
    
    @RequestMapping(value ="/loadstatistics", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadStatistics(ProductIn storage){
		List<ProductIn> res = service.statisticsStorage(storage);
		int count = service.countStorage(storage);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
}
