package com.todo1.api.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.todo1.api.exception.BusinessException;
import com.todo1.api.model.Product;
import com.todo1.api.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	private Product product;
	
	@Before
	public void setup() {
		product = new Product();
		product.setId("5e386bb0462d9d5c0ff62848");
		product.setName("Juguete");
		
	}
	
	@Test
	public void testCreateOk() {
		
		when(productRepository.save(any())).thenReturn(Mono.just(product));
		
		Mono<Product> response = productService.create(Mono.just(new Product()));
	
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateFailed() {
		
		when(productRepository.save(any())).thenThrow(new RuntimeException());
		
		Mono<Product> response = productService.create(Mono.just(new Product()));
	
		assertNotNull(response);
	}
	
	@Test
	public void testUpdateOk() {
		
		when(productRepository.findFirstById(anyString())).thenReturn(Mono.just(product));
		when(productRepository.save(any())).thenReturn(Mono.just(product));
		
		Mono<Product> response = productService.update(Mono.just(new Product()), "5e386bb0462d9d5c0ff62848");
	
		assertNotNull(response);
	}
	
	@Test(expected = BusinessException.class)
	public void testUpdateNotFoundFail() {
	
		when(productRepository.findFirstById(anyString())).thenThrow(new BusinessException());
		
		Mono<Product> response = productService.update(Mono.just(new Product()), "5e386bb0462d9d5c0ff62848");
	
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateFail() {
	
		when(productRepository.findFirstById(anyString())).thenReturn(Mono.just(product));
		when(productRepository.save(any())).thenThrow(new RuntimeException());
		
		Mono<Product> response = productService.update(Mono.just(new Product()), "5e386bb0462d9d5c0ff62848");
	
		assertNotNull(response);
	}
	
	@Test
	public void testGetOneByIdOk() {
	
		when(productRepository.findFirstById(anyString())).thenReturn(Mono.just(product));
		
		Mono<Product> response = productService.getOneById("5e386bb0462d9d5c0ff62848");
	
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOneByIdFail() {
		
		when(productRepository.findFirstById(anyString())).thenThrow(new RuntimeException());
		
		productService.getOneById("5e386bb0462d9d5c0ff62848");
	}
	
	@Test
	public void testGetAllPaginatedOk() {
		
		Pageable pag = PageRequest.of(0, 10);
		when(productRepository.findAllBy(pag)).thenReturn(Flux.just(product));
		
		Flux<Product> response = productService.getAll(0, 10);
	
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetAllPaginatedFail() {
		
		Pageable pag = PageRequest.of(0, 10);
		when(productRepository.findAllBy(pag)).thenThrow(new RuntimeException());
		
		Flux<Product> response = productService.getAll(0, 10);
	
		assertNotNull(response);
	}
	
	@Test
	public void testGetCountOk() {
		when(productRepository.count()).thenReturn(Mono.just(50L));
		
		Mono<Long> response = productService.count();
	
		assertNotNull(response);
		assertNotNull(response.block());
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetCountFali() {
		when(productRepository.count()).thenThrow(new RuntimeException());
		
		productService.count();
	}
	
	@Test(expected = BusinessException.class)
	public void testDeleteNotFoundFail() {
		when(productRepository.findFirstById(anyString())).thenThrow(new BusinessException());
		
		productService.delete("5e386bb0462d9d5c0ff62848");
	}
	
	@Test(expected = BusinessException.class)
	public void testDeleteFail() {
		
		when(productRepository.findFirstById(anyString())).thenReturn(Mono.just(product));
		when(productRepository.delete(any())).thenThrow(new BusinessException());
		
		productService.delete("5e386bb0462d9d5c0ff62848");
	}
}
