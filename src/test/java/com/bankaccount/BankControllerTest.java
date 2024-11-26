package com.bankaccount;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bankaccount.controller.BankController;
import com.bankaccount.entity.BankAccount;
import com.bankaccount.service.BankService;

@ExtendWith(MockitoExtension.class)
class BankControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private BankService bankservice;
	
	@InjectMocks
	private BankController bankcontroller;
	
	@BeforeEach
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(bankcontroller).build();
	}

	@Test
	void testgetAccounts() throws Exception {
		
		
		BankAccount b1 = new BankAccount(1, "Saving",10000,null);
		BankAccount b2 = new BankAccount(2, "Saving",20000,null);
		
		
		when(bankservice.getAllAccounts()).thenReturn(Arrays.asList(b1,b2));
		mockMvc = MockMvcBuilders.standaloneSetup(bankcontroller).build();
		mockMvc.perform(get("/accounts"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data[0].accid").value(1))
				.andExpect(jsonPath("$.data[0].accType").value("Saving"))
				.andExpect(jsonPath("$.data[0].accBalance").value(10000))
				.andExpect(jsonPath("$.data[1].accid").value(2))
				.andExpect(jsonPath("$.data[1].accType").value("Saving"))
				.andExpect(jsonPath("$.data[1].accBalance").value(20000));

		//fail("Not yet implemented");
		
	}
	
	@Test
	public void testUpdateBankDetail() throws Exception
	{
		BankAccount sa = new BankAccount(1,"Saving",10000,null);
		BankAccount da = new BankAccount(2,"Saving",20000,null);
		int amount = 1000;
		
		when(bankservice.getBankById(1)).thenReturn(sa);
		when(bankservice.getBankById(2)).thenReturn(da);
		
		mockMvc.perform(put("/update/{id1}/{id2}/{amount}",1,2,amount)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"accid\": 1,\"accType\":\"Saving\",\"accBalance\":10000}"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value(1))
				.andExpect(jsonPath("$.data.accid").value(sa.getAccid()))
				.andExpect(jsonPath("$.data.accType").value(sa.getAccType()))
				.andExpect(jsonPath("$.data.accBalance").value(9000));
				
		verify(bankservice,times(1)).updateBankDetail(sa);
		verify(bankservice,times(1)).updateBankDetail(da);

				
		
	}

}
