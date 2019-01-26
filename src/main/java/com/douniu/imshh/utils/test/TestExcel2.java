package com.douniu.imshh.utils.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.douniu.imshh.utils.ExcelUtils;
import com.douniu.imshh.utils.SheetData;

public class TestExcel2 {  
      
      
  
    public static void main(String[] args) {  
          
        //获取模板   
        String model = "d:/成品入库记录导出模板.xls" ;   
        File f = new File("d:/test.xlsx");  
  
        //SheetData[] sds = new SheetData[5];  
          
        //创建5个数据sheet  
        //for( int i = 0 ; i < 5 ; i++) {  
            SheetData sd = new SheetData("测试");  
            sd.put("name", "张三");  
            sd.put("age", 13);  
              
            //每个sheet页加入100条测试数据  
            //注意这里可以加入pojo也可以直接使用map,理论上map在这里效率更高一些  
            for(int j = 0 ; j < 2 ; j++) { 
            	TestData td = new TestData(j, j * -1, "t" + j, new Product("code"+j, "model" + j));  
            	List<BillDetail> dtls = new ArrayList<BillDetail>();
            	for (int i = 0; i <3; i++){
            		BillDetail dtl = new BillDetail(null, i, "remark"+i);
            		dtls.add(dtl);
            	}
                td.setDetails(dtls);
                sd.addData(td);
            }  
               
            //sds[i] = sd ;  
        //}  
           
               
        try {  
            ExcelUtils.writeData(model, new FileOutputStream(f) ,sd);  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
           
          
  
    }  
  
}  