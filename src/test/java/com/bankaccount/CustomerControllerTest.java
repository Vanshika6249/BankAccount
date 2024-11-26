package com.bankaccount;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bankaccount.controller.CustomerController;
import com.bankaccount.entity.Customer;
import com.bankaccount.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

	@Mock
	private CustomerService customerservice;
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private CustomerController customercontroller;
	
	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customercontroller).build();
	}
	
	@Test
	public void testGetCustomers() throws Exception
	{
		List<Customer> customers = Arrays.asList(new Customer(1,"Vanshika","Bangalore",null),new Customer(2,"Vanshi","Bathinda",null));
		when(customerservice.getAllCustomers()).thenReturn(customers);
		
		mockMvc = MockMvcBuilders.standaloneSetup(customercontroller).build();
		
		mockMvc.perform(get("/getcustomers"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$.data[0].customerid").value(1))
				.andExpect(jsonPath("$.data[0].customername").value("Vanshika"))
				.andExpect(jsonPath("$.data[0].customerAddress").value("Bangalore"))
				.andExpect(jsonPath("$.data[1].customerid").value(2))
				.andExpect(jsonPath("$.data[1].customername").value("Vanshi"))
				.andExpect(jsonPath("$.data[1].customerAddress").value("Bathinda"));
		
		verify(customerservice,times(1)).getAllCustomers();

				
	}

	
	@Test
	public void testGetById() throws Exception
	{
		Customer c = new Customer(1,"Vanshika","Bangalore",null);
		
		when(customerservice.getCustomerById(1)).thenReturn(c);
		
		mockMvc.perform(get("/customer/{id}",1)
				
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
				assertEquals(1,c.getCustomerid());
				assertEquals("Vanshika",c.getCustomername());
				
			verify(customerservice,times(1)).getCustomerById(1);
		
	}
	
	@Test
	public void testDeleteById() throws Exception
	{
		Customer c = new Customer(1,"Vanshika","Bangalore",null);
		
		when(customerservice.getCustomerById(1)).thenReturn(c);
		
		mockMvc.perform(delete("/delete/{id}",1))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void testUpdatebById() throws Exception
	{
		Customer c = new Customer(1,"Vanshika","Bangalore",null);
		
		Customer uc = new Customer();
		
		uc.setCustomerid(c.getCustomerid());
		uc.setCustomername("Lorraine");
		uc.setCustomerAddress("Punjab");
		
		when(customerservice.getCustomerById(1)).thenReturn(uc);
		
		mockMvc.perform(put("/update/{id}",1)
				
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(uc)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.customername").value("Lorraine"))
				.andExpect(jsonPath("$.data.customerAddress").value("Punjab"));
			
			verify(customerservice,times(1)).addCustomer(uc);	
		

	}
	
	@Test
	public void testDeleteAllCustomers() throws Exception
	{
		List<Customer> customers = Arrays.asList(new Customer(1,"Vanshika","Bangalore",null), new Customer(2,"Vanshe","Punjab",null));
		
		when(customerservice.getAllCustomers()).thenReturn(customers);
		
		mockMvc.perform(delete("/deleteall"))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void testAddCustomer() throws Exception
	{
		Customer c = new Customer(1,"John","Bangalore",null);
		when(customerservice.addCustomer(any(Customer.class))).thenReturn(c);
		
		mockMvc.perform(post("/savecustomer")
				
		.contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(c)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		.andExpect(jsonPath("$.data.customerid").value(1))
		.andExpect(jsonPath("$.data.customername").value("John"))
		.andExpect(jsonPath("$.data.customerAddress").value("Bangalore"));
		
		verify(customerservice,times(1)).addCustomer(any(Customer.class));

				
	}

}
