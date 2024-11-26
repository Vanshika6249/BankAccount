package com.bankaccount.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bankaccount.entity.BankAccount;
import com.bankaccount.service.BankService;

@RestController
public class BankController {
	@Autowired
	private BankService bankserv;
	
	@GetMapping("/accounts")
	public ResponseEntity<?> getAccounts()
	{
		Map<String, Object> JsonOutput = new LinkedHashMap<>();
		List<BankAccount> list = bankserv.getAllAccounts();
		if(!list.isEmpty())
		{
			JsonOutput.put("status", 1);
			JsonOutput.put("data", list);
			return new ResponseEntity<>(JsonOutput,HttpStatus.OK);
		}
		else
		{
			JsonOutput.clear();
			JsonOutput.put("status", 0);
			JsonOutput.put("message", "No Details Found");
			return new ResponseEntity<>(JsonOutput,HttpStatus.NOT_FOUND);

		}
		
	}
	
	@PutMapping("/update/{id1}/{id2}/{amount}")
	public ResponseEntity<?> updateBankDetail(@RequestBody BankAccount bankacc, @PathVariable("id1")int sId, @PathVariable("id2")int dId, @PathVariable("amount")int amount)
	{
		Map<String, Object> JsonOutput = new LinkedHashMap<>();
		BankAccount bank1 = bankserv.getBankById(sId);
		BankAccount bank2 = bankserv.getBankById(dId);
		if((bank1.getAccid()!=0 && bank2.getAccid()!=0) && (!(bank1.getAccid()==(bank2.getAccid()))) && amount>1)
		{
			bank1.setAccid(bankacc.getAccid());
			bank1.setAccType(bankacc.getAccType());
		//	int n = bankacc.getAccBalance();
		//	System.out.print(n);
			bank1.setAccBalance(bankacc.getAccBalance()-amount);
		//	System.out.print(bankacc.getAccBalance()-amount);
			bankserv.updateBankDetail(bank1);
			
			bank2.setAccid(dId);
			bank2.setAccType(bank2.getAccType());
			bank2.setAccBalance(bank2.getAccBalance()+amount);
			
			bankserv.updateBankDetail(bank2);
			
			JsonOutput.put("status", 1);
			JsonOutput.put("message", "Amount Transferred");
			JsonOutput.put("data", bank1);
			
			return new ResponseEntity<>(JsonOutput,HttpStatus.OK);
		}
		else
		{
			JsonOutput.clear();
			JsonOutput.put("status", 0);
			JsonOutput.put("message", "Not Found");
			return new ResponseEntity<>(JsonOutput,HttpStatus.NOT_FOUND);
		}
	}
	

}
