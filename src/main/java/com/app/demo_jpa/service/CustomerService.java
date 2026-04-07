
// src/main/java/com/app/demo_jpa/service/CustomerService.java
package com.app.demo_jpa.service;

import com.app.demo_jpa.model.Customer;
import com.app.demo_jpa.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
	private final CustomerRepository repository;

	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public Customer save(Customer customer) {
		return repository.save(customer);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
