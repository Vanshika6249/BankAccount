package com.bankaccount.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankaccount.dao.CustomerRepository;
import com.bankaccount.entity.Customer;
import com.bankaccount.exception.CustomerNotFoundException;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository custrep;
	
	public List<Customer> getAllCustomers()
	{
		List<Customer> list = (List<Customer>) custrep.findAll();
		return list;
	}
	
	public Customer addCustomer(Customer c)
	{
		custrep.save(c);
		return c;
	}
	
	public Customer getCustomerById(int custId)
	{
		Optional<Customer> custo = custrep.findById(custId);
		Customer customer = null;
		if(custo.isPresent())
		{
			customer = custo.get();
		}
		else
		{
			throw new CustomerNotFoundException("The Customer Isnt Available "+custId);
		}
		return customer;
	}
	
	public void deleteCustomerById(Customer customer)
	{
		custrep.delete(customer);
	}
	
	public void updateCustomer(Customer customer)
	{
		custrep.save(customer);
	}
	
	public void delete()
	{
		custrep.deleteAll();
	}
	
}
