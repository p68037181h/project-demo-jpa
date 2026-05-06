package com.app.demo_jpa.controller;

import com.app.demo_jpa.constant.PathConstant;
import com.app.demo_jpa.model.Customer;
import com.app.demo_jpa.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstant.BASE_PATH)
public class CustomerController {
	
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping(PathConstant.API_CUSTOMERS)
    public List<Customer> getAll() {
        return service.findAll();
    }

    @GetMapping(PathConstant.API_CUSTOMER_BY_ID)
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(PathConstant.API_CUSTOMERS)
    public Customer create(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @PutMapping(PathConstant.API_CUSTOMER_UPDATE)
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        return service.findById(id)
                .map(existing -> {
                    existing.setName(customer.getName());
                    existing.setEmail(customer.getEmail());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(PathConstant.API_CUSTOMER_DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
