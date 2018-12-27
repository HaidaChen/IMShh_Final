package com.douniu.imshh.utils.test;

import java.util.List;

public class TestData {  
    private int id ;  
    private int p_id ;  
    private String name ;  
    private Product product;
    private List<BillDetail> details;
    
    public int getId() {  
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    public int getP_id() {  
        return p_id;  
    }  
    public void setP_id(int p_id) {  
        this.p_id = p_id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    
    
    public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public List<BillDetail> getDetails() {
		return details;
	}
	public void setDetails(List<BillDetail> details) {
		this.details = details;
	}
	public TestData(int id, int p_id, String name, Product product) {
		super();
		this.id = id;
		this.p_id = p_id;
		this.name = name;
		this.product = product;
	}
	
}  