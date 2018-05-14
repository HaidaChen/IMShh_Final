package com.douniu.imshh.finance.order.action;

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
import com.douniu.imshh.finance.order.domain.Order;
import com.douniu.imshh.finance.order.domain.OrderAndDetail;
import com.douniu.imshh.finance.order.domain.OrderDetail;
import com.douniu.imshh.finance.order.service.IOrderService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.ExcelUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/order")
public class OrderAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("订单编号","identify",0));  
		mapper.add(new ExcelBean("订购客户","custName",0));  
		mapper.add(new ExcelBean("订购日期","orderDate",0));   
		mapper.add(new ExcelBean("订购总金额","amount",0));  
		mapper.add(new ExcelBean("备注","remark",0)); 
		mapper.add(new ExcelBean("货号","pdtNo",0)); 
		mapper.add(new ExcelBean("含量", "content", 0));
		mapper.add(new ExcelBean("品名","pdtName",0));
		mapper.add(new ExcelBean("数量(箱)","quantity",0));
		mapper.add(new ExcelBean("单价(¥)","priceRMB",0));
		mapper.add(new ExcelBean("单价($)","priceDollar",0));
		mapper.add(new ExcelBean("合计金额","totlemnt",0));
		mapper.add(new ExcelBean("订单项备注","detailRemark",0)); 
	}
	
	@Autowired
	private IOrderService service;
	
	/**
	 * 查询订单，需要根据订单号、状态、日期、客户查询订单
	 */
	@RequestMapping(value ="/loadorder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryOrder(Order condition){
		List<Order> res = service.query(condition);
		int count = service.count(condition);
		PageResult pr = new PageResult();
		pr.setTotal(count);
		pr.setRows(res);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
	
	/**
	 * 查询订单，需要根据订单号、状态、日期、客户查询订单
	 */
	@RequestMapping(value ="/loadallorder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryAllOrder(Order condition){
		List<Order> res = service.queryNoPage(condition);
		Gson gson = new Gson();
		return gson.toJson(res);
	}
	
	@RequestMapping(value="/findById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String editOrder(String id){
		Order order = service.getById(id);
		Gson gson = new Gson();
        return gson.toJson(order);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int saveOrder(Order order, String orderDetails){
		Gson gson = new Gson();
		List<OrderDetail> details =gson.fromJson(orderDetails, new TypeToken<List<OrderDetail>>() {}.getType());
		order.setDetails(details);
		service.save(order);
        return 1;
	}
	
	@ResponseBody  
    @RequestMapping(value="importorder",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在");  
        }  
          
        in = file.getInputStream();  
        List<List<Object>>  data = ExcelUtil.parseExcel(in, file.getOriginalFilename());
        List<OrderAndDetail> Orders = POIExcelAdapter.toDomainList(data, mapper, OrderAndDetail.class);
        service.batchAdd(Orders);
    }  
    
    @RequestMapping(value = "exportorder", method = RequestMethod.GET)  
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
        	String identify = request.getParameter("identify");
        	String custName = request.getParameter("custName");
        	Date startDate = DateUtil.string2Date(request.getParameter("startDate"));
        	Date endDate = DateUtil.string2Date(request.getParameter("endDate"));
        	
        	Order order = new Order();
        	order.setIdentify(identify);
        	order.setCustName(custName);
        	order.setStartDate(startDate);
        	order.setEndDate(endDate);
        	
            List<OrderAndDetail> Orders = service.queryOrderAndDetail(order);
        	workbook = POIExcelAdapter.toWorkBook(Orders, mapper, OrderAndDetail.class); 
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
