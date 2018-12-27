package com.douniu.imshh.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

public class ReflectionUtil {  
  
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
    /** 
     * 循环向上转型, 获     * @param object : 子类对象 
     * @param methodName : 父类中的方法名 
     * @param parameterTypes : 父类中的方法参数类型 
     * @return 父类中的方法对象 
     */  
      
    public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){  
        Method method = null ;  
          
        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;  
                return method ;  
            } catch (Exception e) {  
                //这里甚么都不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会进入              
            }  
        }  
          
        return null;  
    }  
      
    /** 
     * 直接调用对象方法, 而忽略修饰符(private, protected, default) 
     * @param object : 子类对象 
     * @param methodName : 父类中的方法名 
     * @param parameterTypes : 父类中的方法参数类型 
     * @param parameters : 父类中的方法参数 
     * @return 父类中方法的执行结果 
     */  
      
    public static Object invokeMethod(Object object, String methodName, Class<?> [] parameterTypes,  
            Object [] parameters) {  
        //根据 对象、方法名和对应的方法参数 通过取 Method 对象  
        Method method = getDeclaredMethod(object, methodName, parameterTypes) ;  
          
        //抑制Java对方法进行检查,主要是针对私有方法而言  
        method.setAccessible(true) ;  
          
            try {  
                if(null != method) {  
                      
                    //调用object 的 method 所代表的方法，其方法的参数是 parameters  
                    return method.invoke(object, parameters) ;  
                }  
            } catch (IllegalArgumentException e) {  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                e.printStackTrace();  
            } catch (InvocationTargetException e) {  
                e.printStackTrace();  
            }  
          
        return null;  
    }  
  
    /** 
     * 循环向上转型, 获     * @param object : 子类对象 
     * @param fieldName : 父类中     * @return 父类中     */  
      
    public static Field getDeclaredField(Object object, String fieldName){  
        Field field = null ;  
          
        Class<?> clazz = object.getClass() ;  
          
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                field = clazz.getDeclaredField(fieldName) ;  
                return field ;  
            } catch (Exception e) {  
                //这里甚么都不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会进入                  
            }   
        }  
      
        return null;  
    }     
    
    public static List<Field> getDeclaredFields(Object object, String[] fieldNames){
    	List<Field> fields = new ArrayList<Field>();
    	Class<?> clazz = object.getClass() ;  
        if (fieldNames == null){
	        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
	            try {  
	            	for(Field field : clazz.getDeclaredFields()){
	            		fields.add(field);
	            	}                  
	            } catch (Exception e) {  
	                e.printStackTrace();
	            }   
	        } 
        }else{
        	for (String fieldName : fieldNames){
        		fields.add(getDeclaredField(object, fieldName));
        	}
        }
    	return fields;
    }
    
      
    /** 
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也     * @param object : 子类对象 
     * @param fieldName : 父类中     * @param value : 将要设置的值 
     */  
      
    public static void setFieldValue(Object object, String fieldName, Object value){  
    	try {
    		
	    	//String _fieldName = fieldName;
	    	if (fieldName.indexOf(".") > 0){
	        	String objName = fieldName.substring(0, fieldName.indexOf("."));
	        	Field field = getDeclaredField(object, objName);
        		field.setAccessible(true);  
				Object _obj = field.getType().newInstance();
				String _fieldName = fieldName.substring(fieldName.indexOf(".")+1);
				setFieldValue(_obj, _fieldName, value);
				field.set(object, _obj) ; 
	        } else{
	        	//根据 对象和属性名通过取 Field对象  
	            Field field = getDeclaredField(object, fieldName) ;  
	              
	            //抑制Java对其的检查  
	            field.setAccessible(true) ;	            
                //将 object 中 field 所代表的值 设置为 value  
            	String sdate = value.toString();
        		/*if (sdate.equals("") || sdate.equals("null"))
        			return;*/
            	if (field.getType() == Date.class){
            		if (!StringUtils.isEmpty(sdate))
            			value = format.parse(sdate);
            		else
            			return;
            	}
            	if (field.getType() == int.class || field.getType() == Integer.class){
            		if (!StringUtils.isEmpty(sdate))
            			value = new Integer(value.toString());
            		else value = 0;
            	}
            	if (field.getType() == float.class || field.getType() == Float.class){
            		if (!StringUtils.isEmpty(sdate))
            			value = new Float(value.toString());
            		else value = 0f;
            	}
            	field.set(object, value); 
	        }
    	} catch (Exception e) {  
            e.printStackTrace();  
        } 
    }  
      
    /** 
     * 直接读的属性值, 忽略 private/protected 修饰符, 也     * @param object : 子类对象 
     * @param fieldName : 父类中     * @return : 父类中     */  
      
    public static Object getFieldValue(Object object, String fieldName){  
    	try { 
	    	if (fieldName.indexOf(".") > 0){
	    		String objName = fieldName.substring(0, fieldName.indexOf("."));
	    		Field field = getDeclaredField(object, objName);
	    		field.setAccessible(true);
	    		Object _object = field.get(object);
	    		if (_object instanceof List){
	    			return _object;
	    		}
	    		return getFieldValue(_object, fieldName.substring(fieldName.indexOf(".") + 1));
	    	}else{
	    		//根据 对象和属性名通过取 Field对象  
	            Field field = getDeclaredField(object, fieldName) ;  
	              
	            //抑制Java对其的检查  
	            field.setAccessible(true) ;
                //获的属性值  
                return field.get(object) ;  
	    	}        
              
        } catch(Exception e) {  
            e.printStackTrace() ;  
        }  
          
        return null;  
    }  
}  