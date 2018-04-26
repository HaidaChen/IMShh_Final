package com.douniu.imshh.busdata.product.action;

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
import org.springframework.web.servlet.ModelAndView;

import com.douniu.imshh.busdata.product.domain.Product;
import com.douniu.imshh.busdata.product.service.IProductService;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.ExcelUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/pdt")
public class ProductAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("产品编码","code",0));  
		mapper.add(new ExcelBean("产品名称","name",0));  
		mapper.add(new ExcelBean("规格","specification",0));   
		mapper.add(new ExcelBean("型号","model",0));  
		mapper.add(new ExcelBean("上线日期","lineDate",0));  
		mapper.add(new ExcelBean("下线日期","downlineDate",0));  
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IProductService service;
	
	@RequestMapping("/main")
    public ModelAndView enter(Product pdt){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/busdata/pdt/overview");
        return mav;
    }
	
	@RequestMapping("/edit")
	public ModelAndView edit(Product pdt){
		ModelAndView mav = new ModelAndView();
		if (pdt.getId() != ""){
			Product product = service.getById(pdt.getId());
			mav.addObject("pdt", product);
		}
        mav.setViewName("/busdata/pdt/edit");
        return mav;
	}
	
	@RequestMapping("/save")
	public ModelAndView save(Product pdt){
		service.save(pdt);
        return enter(pdt);
	}
	
	@RequestMapping(value ="/loadpdt", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadProduct(Product pdt){
		List<Product> res = service.query(pdt);
		int count = service.count(pdt);

		PageResult pr = new PageResult();
		pr.setTotal(count);
		pr.setRows(res);
				
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String resJson = gson.toJson(pr);
		return resJson;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}
	
	
	
    @ResponseBody  
    @RequestMapping(value="ajaxUpload",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在");  
        }  
          
        in = file.getInputStream();  
        List<List<Object>>  data = ExcelUtil.parseExcel(in, file.getOriginalFilename());
        List<Product> products = POIExcelAdapter.toDomainList(data, mapper, Product.class);
        service.batchAdd(products);
    }  
    
    @RequestMapping(value = "downloadExcel", method = RequestMethod.GET)  
    @ResponseBody  
    public void downloadExcel(HttpServletRequest request,HttpServletResponse response,HttpSession session){  
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
        	Product product = new Product();
        	product.setCondition(condition);
            List<Product> products = service.queryNoPage(product);
        	workbook = POIExcelAdapter.toWorkBook(products, mapper, Product.class); 
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
