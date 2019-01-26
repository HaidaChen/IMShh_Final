package com.douniu.imshh.utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class ImportAndExportUtil {
	public static List<List<Object>> importPreprocess(HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
        
        InputStream in =null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在");  
        }  
          
        in = file.getInputStream();  
        List<List<Object>>  data = ExcelUtil.parseExcel(in, file.getOriginalFilename());
        return data;
	}
	
	public static void exportPreprocess(HttpServletResponse response, String fileName){
		response.reset();  
        
        response.setHeader("Content-Disposition", "attachment;filename=" +fileName+".xlsx");  
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
	}
	
	public static void exportProcess(HttpServletResponse response, Workbook workbook){
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
	
	public static void export(String tmp, SheetData data,HttpServletRequest request, HttpServletResponse response){
		String tmpPath = request.getRealPath("/templater/export/"+tmp);
        
        try {
        	response.setHeader("Content-Disposition", "attachment;filename=" + new String(data.getName().getBytes("gbk"),"ISO-8859-1")+".xls");
            response.setContentType("charset=UTF-8");  
            response.setHeader("Pragma", "no-cache");  
            response.setHeader("Cache-Control", "no-cache");  
            response.setDateHeader("Expires", 0); 
			OutputStream out = response.getOutputStream();
			ExcelUtils.writeData(tmpPath, out, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static void export(Workbook tmp, SheetData data,HttpServletRequest request, HttpServletResponse response){
		
        try {
        	response.setHeader("Content-Disposition", "attachment;filename=" + new String(data.getName().getBytes("gbk"),"ISO-8859-1")+".xls");
            response.setContentType("charset=UTF-8");  
            response.setHeader("Pragma", "no-cache");  
            response.setHeader("Cache-Control", "no-cache");  
            response.setDateHeader("Expires", 0); 
			OutputStream out = response.getOutputStream();
			ExcelUtils.writeData(tmp, out, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
