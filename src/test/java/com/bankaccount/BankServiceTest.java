package com.bankaccount;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bankaccount.dao.BankRepository;
import com.bankaccount.entity.BankAccount;
import com.bankaccount.service.BankService;

class BankServiceTest {
	
	@InjectMocks
	private BankService bankservice;
	
	@Mock
	private BankRepository bankrepository;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testgetAllAccounts()
	{
		List<BankAccount> bank = new ArrayList<>();
		bank.add(new BankAccount(1,"Saving",10000,null));
		bank.add(new BankAccount(2,"Saving",20000,null));
		
		when(bankrepository.findAll()).thenReturn(bank);
		
		List<BankAccount> list = bankservice.getAllAccounts();
		assertEquals(bank.size(),list.size());
		assertEquals("Saving",list.get(0).getAccType());
		assertEquals("Saving",list.get(0).getAccType());

	}
	
	@Test
	public void testgetBankbyId()
	{
		BankAccount b = new BankAccount(2,"Saving",20000,null);
		when(bankrepository.findById(1)).thenReturn(Optional.of(b));
		assertEquals("Saving",b.getAccType());
		assertEquals(2,b.getAccid());
	}
	
	@Test
	public void testupdateBankDetail()
	{
		BankAccount b = new BankAccount(3,"Unknown",30000,null);
		
		bankrepository.save(b);
		
		b.setAccid(b.getAccid());
		b.setAccType("Current");
		b.setAccBalance(30000);
		
		bankrepository.save(b);
		
		when(bankrepository.findById(b.getAccid())).thenReturn(Optional.of(b));
		
		assertEquals("Current",b.getAccType());
	}
	
}
