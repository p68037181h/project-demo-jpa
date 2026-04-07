package com.app.demo_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.demo_jpa.model.Product;
import com.app.demo_jpa.repository.ProductRepository;

import java.util.List;


@RestController
@RequestMapping("/demo-jpa")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/api/products")
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @PostMapping("/api/products")
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/api/product/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return productRepository.findById(id)
                .map(updatedProduct -> {
                            updatedProduct.setName(product.getName());
                            updatedProduct.setPrice(product.getPrice());
                            productRepository.save(updatedProduct);
                            return ResponseEntity.ok(updatedProduct);

                        }
                )
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @DeleteMapping("/api/product/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
