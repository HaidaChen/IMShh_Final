package com.douniu.imshh.utils.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;

import com.douniu.imshh.utils.ExcelUtils;
import com.douniu.imshh.utils.SheetData;

public class TestRange {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//获取模板   
        String model = "d:/合并测试模板.xls" ;   
        File f = new File("d:/test1.xlsx"); 
        SheetData sd = new SheetData("测试合并");  
        sd.put("test", "期末考试");
        sd.put("tdate", new Date());
        List<Student> stds = new ArrayList<Student>();
        stds.add(new Student("张三", "语文", 88));
        stds.add(new Student("张三", "数学", 89));
        
        
        sd.addDatas(stds);
        
        List<CellRangeAddress> ranges = new ArrayList<CellRangeAddress>();
        ranges.add(new CellRangeAddress(3, 4, 0, 0));
        
        sd.setRanges(ranges);
        
          
        try {  
            ExcelUtils.writeData(model, new FileOutputStream(f) ,sd);  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}

}
