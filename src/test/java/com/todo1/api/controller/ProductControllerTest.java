package com.todo1.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;


import com.todo1.api.controller.dto.ProductDto;
import com.todo1.api.converter.ProductConverter;
import com.todo1.api.model.Product;
import com.todo1.api.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@InjectMocks
	private ProductController productController;
	
	@Mock
	private ProductService productService;
	
	@Spy
	private ProductConverter productConverter;
	
	private Product product;
	
	@Before
	public void setup() {
		product = new Product();
		product.setId("5e386bb0462d9d5c0ff62848");
		product.setName("Juguete");
	}
	
	@Test
	public void testGetAllOk() {
		when(productService.getAll(anyInt(), anyInt())).thenReturn(Flux.just(product));
		
		Flux<ProductDto> response = productController.getAll(0, 10);
		
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetAllFail() {
		when(productService.getAll(anyInt(), anyInt())).thenThrow(new RuntimeException());
		
		productController.getAll(0, 10);
	}
	
	@Test
	public void testGetCountOk() {
		when(productService.count()).thenReturn(Mono.just(10L));
		
		Mono<Long> response = productController.getCount();
		
		assertNotNull(response);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetCountFail() {
		when(productService.count()).thenThrow(new RuntimeException());
		
		productController.getCount();

	}
	
	@Test
	public void testGetOneByIdOk() {
		when(productService.getOneById(anyString())).thenReturn(Mono.just(product));
		
		Mono<ProductDto> response = productController.getOneById("5e386bb0462d9d5c0ff62848");
		
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOneByIdFail() {
		when(productService.getOneById(anyString())).thenThrow(new RuntimeException());
		
		productController.getOneById("5e386bb0462d9d5c0ff62848");
		
	}
	
	@Test
	public void testCreateOk() {
		when(productService.create(any())).thenReturn(Mono.just(product));
		
		ProductDto productDto = new ProductDto();
		productDto.setId("5e386bb0462d9d5c0ff62848");;
		productDto.setName("Accesorio");
		
		Mono<ProductDto> response = productController.create(productDto);
		
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateFail() {
		when(productService.create(any())).thenThrow(new RuntimeException());
		
		ProductDto productDto = new ProductDto();
		productDto.setId("5e386bb0462d9d5c0ff62848");;
		productDto.setName("Accesorio");
		
		productController.create(productDto);

	}
	
	@Test
	public void testUpdateOk() {
		when(productService.update(any(), anyString())).thenReturn(Mono.just(product));
		
		ProductDto productDto = new ProductDto();
		productDto.setId("5e386bb0462d9d5c0ff62848");;
		productDto.setName("Accesorio");
		
		Mono<ProductDto> response = productController.update("5e386bb0462d9d5c0ff62848", productDto);
		
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateFail() {
		when(productService.update(any(), anyString())).thenThrow(new RuntimeException());
		
		ProductDto productDto = new ProductDto();
		productDto.setId("5e386bb0462d9d5c0ff62848");;
		productDto.setName("Accesorio");
		
		productController.update("5e386bb0462d9d5c0ff62848", productDto);

	}
}
