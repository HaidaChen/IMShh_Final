package com.douniu.imshh.busdata.material.action;

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

import com.douniu.imshh.busdata.material.domain.Material;
import com.douniu.imshh.busdata.material.service.IMaterialService;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.ExcelUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/mtl")
public class MaterialAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("品名","name",0));  
		mapper.add(new ExcelBean("规格","specification",0));  
		mapper.add(new ExcelBean("单位","unit",0));   
		mapper.add(new ExcelBean("分类","category",0));  
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IMaterialService service;
	
	@RequestMapping("/main")
    public ModelAndView enter(Material mtl){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/busdata/mtl/overview");
        return mav;
    }
	
	@RequestMapping("/edit")
	public ModelAndView edit(Material mtl){
		ModelAndView mav = new ModelAndView();
		if (mtl.getId() != ""){
			Material material = service.getById(mtl.getId());
			mav.addObject("mtl", material);
		}
        mav.setViewName("/busdata/mtl/edit");
        return mav;
	}
	
	@RequestMapping("/save")
	public ModelAndView save(Material mtl){
		service.save(mtl);
        return enter(mtl);
	}
	
	@RequestMapping(value ="/loadmtl", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadProduct(Material mtl){
		List<Material> res = service.query(mtl);
		int count = service.count(mtl);
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new Gson();
        return gson.toJson(pr);
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
        List<Material> materials = POIExcelAdapter.toDomainList(data, mapper, Material.class);
        service.batchAdd(materials);
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
        	Material material = new Material();
        	material.setCondition(condition);
            List<Material> products = service.queryNoPage(material);
        	workbook = POIExcelAdapter.toWorkBook(products, mapper, Material.class); 
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
