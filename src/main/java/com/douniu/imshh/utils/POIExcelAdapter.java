package com.douniu.imshh.utils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

public class POIExcelAdapter {
	
	public static Workbook toWorkBook(List objects, List<ExcelBean> mapper, Class clazz) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException{
		Map<Integer,List<ExcelBean>>map=new LinkedHashMap<>();  
        map.put(0, mapper);  
        return ExcelUtil.createExcelFile(clazz, objects, map, "业务数据");  
	}
	
	public static <T> List<T> toDomainList(List<List<Object>> data, List<ExcelBean> mapper, Class<T> clazz){
		List<T> list = new ArrayList<T>();
		try{
			for (List<Object> obj : data){
				T t = clazz.newInstance();
				int i = 0;
				for (ExcelBean excelBean : mapper){
					String fieldName = excelBean.getPropertyName();
					ReflectionUtil.setFieldValue(t, fieldName, String.valueOf(obj.get(i++)));
				}
				list.add(t);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
}
