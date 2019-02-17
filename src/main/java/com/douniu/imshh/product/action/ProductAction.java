package com.douniu.imshh.product.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.Product;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.service.IProductService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;

@Controller
@RequestMapping("/pdt")
public class ProductAction {
	@Autowired
	private IProductService service;
	
	@Authorization("0502")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(ProductFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("0502")
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(ProductFilter filter){
		List<Product> prdocuts = service.query(filter);
		return GsonUtil.toJson(prdocuts);
	}
	
	@Authorization("0502")
	@RequestMapping(value="/getProductById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductById(String id){
		Product product = service.getById(id);
		return GsonUtil.toJson(product);
	}
	
	@RequestMapping(value="/validateUnique", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String validateUnique(String id, String code){
		boolean result = false;
		if (StringUtils.isEmpty(id)){
			if (service.getByCode(code) == null) result = true; 
		}else{
			if (service.getById(id).getCode().equals(code)){
				result = true; 
			}else{
				if (service.getByCode(code) == null) result = true; 
			}
		}
		
		Map<String, Boolean> map = new HashMap<>();
		map.put("valid", result);
		return GsonUtil.toJson(map);
	}
	
	@Authorization("0502")
	@RequestMapping(value="/addProduct", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void addProduct(Product product){
		service.newProduct(product);
	}
	
	@RequestMapping(value="/updateProduct", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateProduct(Product product){
		service.updateProduct(product);
	}
	
	@Authorization("0502")
	@RequestMapping(value="/deleteProduct")
	@ResponseBody
	public void deleteProduct(String id){
		service.delete(id);
	}
	
	@Authorization("0502")
	@RequestMapping(value="importProduct",method={RequestMethod.GET,RequestMethod.POST}, produces = "text/html; charset=utf-8")  
    @ResponseBody
	public String importProduct(HttpServletRequest request) throws Exception{
		List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
        List<Product> products = new ArrayList<>();
		for (List<Object> rowData : data){
			Product pdt = new Product();
			pdt.setCode(rowData.get(0).toString());
			pdt.setName(rowData.get(1).toString());
			pdt.setSpecification(rowData.get(2).toString());
			pdt.setModel(rowData.get(3).toString());
			if (StringUtils.isEmpty(rowData.get(4).toString()))pdt.setStorage(0);
			else pdt.setStorage(new Integer(rowData.get(4).toString()));
			pdt.setRemark(rowData.get(5).toString());
			products.add(pdt);
		}
		List<ImportException> checkResults = service.checkImport(products);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importProduct(products);
        return "success";
	}
	
	@Authorization("0502")
	@RequestMapping(value = "exportProduct", method = RequestMethod.GET, produces = "text/html; charset=utf-8")  
    @ResponseBody  
	public void exportProduct(HttpServletRequest request,HttpServletResponse response, ProductFilter filter){
		SheetData data = new SheetData("成品列表");
		/*data.put("name", filter.getName());
		data.put("ctgCode", filter.getCtgCode());
		data.put("specification", filter.getSpecification());
		data.put("lowerStorage", filter.getLowerStorage());
		data.put("upperStorage", filter.getUpperStorage());
		data.put("remark", filter.getRemark());*/
		
		List<Product> products = service.exportProduct(filter);
		data.addDatas(products);
		ImportAndExportUtil.export("成品列表.xls", data, request, response);
	}
	/*private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("货号","code",0));  
		mapper.add(new ExcelBean("产品名称","name",0));  
		mapper.add(new ExcelBean("规格","specification",0));   
		mapper.add(new ExcelBean("型号","model",0)); 
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IProductService service;
	
	@RequestMapping(value="/edit", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String edit(Product pdt){
		Product product = service.getById(pdt.getId());
		Gson gson = new Gson();
        return gson.toJson(product);
	}
	
	@RequestMapping(value="/findbycode", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findByCode(String code){
		Product product = service.getByCode(code);
		Gson gson = new Gson();
        return gson.toJson(product);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(Product pdt){
		service.save(pdt);
        return 1;
	}
	
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(Product pdt){
		List<Product> list = service.query(pdt);
		return GsonUtil.toJson(list);
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
	
	@RequestMapping(value ="/loadallpdt", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadAllProduct(Product pdt){
		pdt.setPageSize(10000);
		List<Product> res = service.query(pdt);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String resJson = gson.toJson(res);
		return resJson;
	}	
	
	@RequestMapping(value ="/loadpdtbyorder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryByOrder(String identify){
		List<Product> res = service.queryByOrder(identify);
		
		Gson gson = new Gson();
        return gson.toJson(res);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}
	
	
    @ResponseBody  
    @RequestMapping(value="importproduct",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  importProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {  
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
    
    @RequestMapping(value = "exportproduct", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportProduct(HttpServletRequest request,HttpServletResponse response,HttpSession session){  
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
    }  */
}
