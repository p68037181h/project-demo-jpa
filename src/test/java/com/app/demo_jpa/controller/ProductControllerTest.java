
package com.app.demo_jpa.controller;

import com.app.demo_jpa.model.Product;
import com.app.demo_jpa.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

	@Mock
	private ProductService service;

	@InjectMocks
	private ProductController controller;

	private Product product;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		product = new Product();
		product.setId(1L);
		product.setName("Test Product");
		product.setPrice(100.0);
	}

	@Test
	void testGetAll() {
		when(service.findAll()).thenReturn(Arrays.asList(product));
		List<Product> products = controller.getAll();
		assertEquals(1, products.size());
		assertEquals("Test Product", products.get(0).getName());
	}

	@Test
	void testGetById_Found() {
		when(service.findById(1L)).thenReturn(Optional.of(product));
		ResponseEntity<Product> response = controller.getById(1L);
		assertEquals(200, response.getStatusCode().value());
		assertEquals(product, response.getBody());
	}

	@Test
	void testGetById_NotFound() {
		when(service.findById(2L)).thenReturn(Optional.empty());
		ResponseEntity<Product> response = controller.getById(2L);
		assertEquals(404, response.getStatusCode().value());
		assertNull(response.getBody());
	}

	@Test
	void testCreate() {
		when(service.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
		Product created = controller.create(product);
		assertEquals(product, created);
	}

	@Test
	void testUpdate_Found() {
		Product updated = new Product();
		updated.setName("Updated");
		updated.setPrice(200.0);

		when(service.findById(1L)).thenReturn(Optional.of(product));
		when(service.save(any(Product.class))).thenReturn(product);

		ResponseEntity<Product> response = controller.update(1L, updated);
		assertEquals(200, response.getStatusCode().value());
		verify(service).save(product);
	}

	@Test
	void testUpdate_NotFound() {
		when(service.findById(2L)).thenReturn(Optional.empty());
		ResponseEntity<Product> response = controller.update(2L, product);
		assertEquals(404, response.getStatusCode().value());
	}

	@Test
	void testDelete_Found() {
		when(service.findById(1L)).thenReturn(Optional.of(product));
		doNothing().when(service).deleteById(1L);
		ResponseEntity<Void> response = controller.delete(1L);
		assertEquals(204, response.getStatusCode().value());
	}

	@Test
	void testDelete_NotFound() {
		when(service.findById(2L)).thenReturn(Optional.empty());
		ResponseEntity<Void> response = controller.delete(2L);
		assertEquals(404, response.getStatusCode().value());
	}
}
