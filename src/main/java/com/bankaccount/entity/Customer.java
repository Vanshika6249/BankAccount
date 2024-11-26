package com.bankaccount.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerid;
	private String customername;
	private String customerAddress;
	@OneToMany(cascade = CascadeType.ALL)
	@JsonManagedReference
	//private BankAccount bankacc;
	private List<BankAccount> bankacc;
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Customer(int customerid, String customername, String customerAddress) {
		super();
		this.customerid = customerid;
		this.customername = customername;
		this.customerAddress = customerAddress;
	}
	
	public Customer(int customerid, String customername, String customerAddress, List<BankAccount> bankacc) {
		super();
		this.customerid = customerid;
		this.customername = customername;
		this.customerAddress = customerAddress;
		this.bankacc = bankacc;
	}
	
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public List<BankAccount> getBankaccount() {
		return bankacc;
	}
	public void setBankaccount(List<BankAccount> bankacc) {
		this.bankacc = bankacc;
	}
	@Override
	public String toString() {
		return "Customer [customerid=" + customerid + ", customername=" + customername + ", customerAddress="
				+ customerAddress + ", bankaccount=" + bankacc + "]";
	}
	
	
	
	
}
