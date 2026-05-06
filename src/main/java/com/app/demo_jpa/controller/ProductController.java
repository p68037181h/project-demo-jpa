package com.app.demo_jpa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.demo_jpa.constant.PathConstant;
import com.app.demo_jpa.model.Product;
import com.app.demo_jpa.service.ProductService;

import java.util.List;


@RestController
@RequestMapping(PathConstant.BASE_PATH)
public class ProductController {

	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}
	
    @GetMapping(PathConstant.API_PRODUCTS)
    public List<Product> getAll() {
        return service.findAll();
    }

    @GetMapping(PathConstant.API_PRODUCT_BY_ID)
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @PostMapping(PathConstant.API_PRODUCTS)
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping(PathConstant.API_PRODUCT_UPDATE)
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return service.findById(id)
                .map(updatedProduct -> {
                     updatedProduct.setName(product.getName());
                     updatedProduct.setPrice(product.getPrice());          
                     return ResponseEntity.ok(service.save(updatedProduct));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(PathConstant.API_PRODUCT_DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
