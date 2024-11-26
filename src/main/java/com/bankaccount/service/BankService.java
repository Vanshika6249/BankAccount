package com.bankaccount.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankaccount.dao.BankRepository;
import com.bankaccount.entity.BankAccount;
import com.bankaccount.exception.BankNotFoundException;

@Service
public class BankService {
	@Autowired
	private BankRepository bankrep;
	
	public List<BankAccount> getAllAccounts()
	{
		List<BankAccount> list = (List<BankAccount>) bankrep.findAll();
		return list;
	}
	
	public BankAccount getBankById(int id)
	{
		Optional<BankAccount> bank = bankrep.findById(id);
		BankAccount account = null;
		if(bank.isPresent())
		{
			account = bank.get();
		}
		else
		{
			throw new BankNotFoundException("The Bank Account Isnt Available "+id);
		}
		return account;
	}
	
	public void updateBankDetail(BankAccount bank)
	{
		bankrep.save(bank);
	}
}
