package com.abstractandtangibles.spring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abstractandtangibles.spring.entity.Customer;
import com.abstractandtangibles.spring.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// autowire the CustomerService
	@Autowired
	private CustomerService customerService;
	
	// add mapping for GET /customers
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		
		return customerService.getCustomers();
		
	}
	
	// add mapping for GET /customers/{customerId}
	@GetMapping("/customers/{customerId}")
	public Customer getCustomers(@PathVariable int customerId) {
		
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if(theCustomer == null) {
			throw new CustomerNotFoundException("Cusomer id not found - "+ customerId);
		}
		
		return theCustomer;
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		//also just in case the pass an id in JSON... set id to 0
		//this is to force a save of new item... instead of update
		
		theCustomer.setId(0); //when hibernate sees 0, it performs create operation and performs update operate if it sees 1
	
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	// add mapping for GET /customers/{customerId}
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomers(@PathVariable int customerId) {
		
		Customer tempCustomer = customerService.getCustomer(customerId);
		
		//throw exception if customer with such id does not exist
		if(tempCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
		
		customerService.deleteCustomer(customerId);
		
		return "Deleted customer id - " + customerId;
	}
}


















