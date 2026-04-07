// src/main/java/com/app/demo_jpa/repository/CustomerRepository.java
package com.app.demo_jpa.repository;

import com.app.demo_jpa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
