package com.douniu.imshh.finance.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Voucher;
import com.douniu.imshh.finance.domain.VoucherEntry;
import com.douniu.imshh.finance.service.IVoucherService;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/voc")
public class VoucherAction {
	@Autowired
	private IVoucherService service;
	@Autowired
	private IParameterService pservice;
	
	@Authorization("0404")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(HttpSession session ,FinanceFilter filter){
		User user = (User)session.getAttribute("user");
		List<Role> roles = user.getRoles();
		boolean filterRow = true;
		for(Role role : roles){
			if ("02".equals(role.getId())){
				filterRow = false;
				break;
			}
		}
		
		if (filterRow){
			filter.setPreparedBy(user.getUserName());
		}

		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("0404")
	@RequestMapping(value ="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		Voucher voc = service.getById(id);
		return GsonUtil.toJson(voc, null);
	}
	
	@Authorization("0403")
	@RequestMapping(value="/addVoucher", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void addVoucher(Voucher voucher, String billItem){
		List<VoucherEntry> _entries = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<VoucherEntry>>(){}.getType());
		List<VoucherEntry> entries = new ArrayList<>();
		for (VoucherEntry entry : _entries){
			if (entry.getSubject()!= null && !StringUtils.isEmpty(entry.getSubject().getId())){
				entries.add(entry);
			}
		}
		voucher.setEntries(entries);
		service.newVoucher(voucher);
		//pservice.setParam("bill.materialin.code", materialIn.getNumber());
	}
	
	@Authorization("0403")
	@RequestMapping(value="/updateVoucher", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateVoucher(Voucher voucher, String billItem){
		List<VoucherEntry> _entries = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<VoucherEntry>>(){}.getType());
		List<VoucherEntry> entries = new ArrayList<>();
		for (VoucherEntry entry : _entries){
			if (entry.getSubject()!= null && !StringUtils.isEmpty(entry.getSubject().getId())){
				entries.add(entry);
			}
		}
		voucher.setEntries(entries);
		service.updateVoucher(voucher);		
		//pservice.setParam("bill.materialin.code", materialIn.getNumber());
	}
	
	@Authorization("0403")
	@RequestMapping(value="/deleteVoucher", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void deleteVoucher(String id){
		service.deleteVoucher(id);
	}
	
	@RequestMapping(value ="/getNextNumber", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getNextNumber(Voucher voucher){
		if (voucher.getWord() == 0){voucher.setWord(1);}
		if (voucher.getBillDate() == null){voucher.setBillDate(new Date());}
		return ""+service.getNexNumber(voucher);
	}
	
	@RequestMapping(value ="/allBillPeriod", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String allBillPeriod(FinanceFilter filter){
		return GsonUtil.toJson(pservice.getBillPeriod(), null);
	}
}
