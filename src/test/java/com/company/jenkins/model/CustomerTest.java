package com.company.jenkins.model;

import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {

	private Customer customer;
	private Long id = 1L;
	private String firstName = "firstName"; 
		
	public CustomerTest()
	{
		customer = new Customer();
	}
	
	@Test
	public void TestSetAndGetId()
	{
		customer.setId(id);
		Assert.assertTrue(id.equals(customer.getId()));
	}
	
	@Test
	public void TestSetAndGetFirstId()
	{
		customer.setFirstName(firstName);
		Assert.assertTrue(firstName.equals(customer.getFirstName()));
	}
			  
}
