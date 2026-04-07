package com.app.demo_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.demo_jpa.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
