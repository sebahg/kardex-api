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
import com.todo1.api.model.Stock;
import com.todo1.api.repository.StockRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

	@InjectMocks
	private StockService productService;
	
	@Mock
	private StockRepository stockRepository;
	
	private Stock stock;
	
	@Before
	public void setup() {
		stock = new Stock();
		stock.setCount(20L);
		stock.setId("5e386bb0462d9d5c0ff62848");
		stock.setProductId("5e386bb0462d9d5c0ff62848");
	}
	
	@Test
	public void testCreateOk() {
		
		when(stockRepository.save(any())).thenReturn(Mono.just(stock));
		
		Mono<Stock> response = productService.create(Mono.just(new Stock()));
	
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateFailed() {
		
		when(stockRepository.save(any())).thenThrow(new RuntimeException());
		
		Mono<Stock> response = productService.create(Mono.just(new Stock()));
	
		assertNotNull(response);
	}
	
	@Test
	public void testUpdateOk() {
		
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		when(stockRepository.save(any())).thenReturn(Mono.just(stock));
		
		Mono<Stock> response = productService.update(Mono.just(new Stock()), "5e386bb0462d9d5c0ff62848");
	
		assertNotNull(response);
	}
	
	@Test(expected = BusinessException.class)
	public void testUpdateNotFoundFail() {
	
		when(stockRepository.findFirstById(anyString())).thenThrow(new BusinessException());
		
		Mono<Stock> response = productService.update(Mono.just(new Stock()), "5e386bb0462d9d5c0ff62848");
	
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateFail() {
	
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		when(stockRepository.save(any())).thenThrow(new RuntimeException());
		
		Mono<Stock> response = productService.update(Mono.just(new Stock()), "5e386bb0462d9d5c0ff62848");
	
		assertNotNull(response);
	}
	
	@Test
	public void testAddStockOk() {
		
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		when(stockRepository.save(any())).thenReturn(Mono.just(stock));
		
		Mono<Stock> response = productService.addStock("5e386bb0462d9d5c0ff62848", 15L);
	
		assertNotNull(response);
	}
	
	@Test( expected = BusinessException.class)
	public void testAddStockNegativeCountFail() {
		
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		
		Mono<Stock> response = productService.addStock("5e386bb0462d9d5c0ff62848", -15L);
	
		assertNotNull(response);
	}
	
	@Test
	public void testRemoveStockOk() {
		
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		when(stockRepository.save(any())).thenReturn(Mono.just(stock));
		
		Mono<Stock> response = productService.removeStock("5e386bb0462d9d5c0ff62848", 15L);
	
		assertNotNull(response);
	}
	
	@Test( expected = BusinessException.class)
	public void testRemoveStockNegativeCountFail() {
		
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		
		Mono<Stock> response = productService.removeStock("5e386bb0462d9d5c0ff62848", -15L);
	
		assertNotNull(response);
	}
	
	@Test( expected = BusinessException.class)
	public void testRemoveStockNotEnoughFail() {
	
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		
		Mono<Stock> response = productService.removeStock("5e386bb0462d9d5c0ff62848", 25L);
	
		assertNotNull(response);
	}
	
	@Test
	public void testGetOneByIdOk() {
	
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		
		Mono<Stock> response = productService.getOneById("5e386bb0462d9d5c0ff62848");
	
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOneByIdFail() {
		
		when(stockRepository.findFirstById(anyString())).thenThrow(new RuntimeException());
		
		productService.getOneById("5e386bb0462d9d5c0ff62848");
	}
		
	@Test
	public void testGetAllPaginatedOk() {
		
		Pageable pag = PageRequest.of(0, 10);
		when(stockRepository.findAllBy(pag)).thenReturn(Flux.just(stock));
		
		Flux<Stock> response = productService.getAll(0, 10);
	
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetAllPaginatedFail() {
		
		Pageable pag = PageRequest.of(0, 10);
		when(stockRepository.findAllBy(pag)).thenThrow(new RuntimeException());
		
		productService.getAll(0, 10);
	}
	
	@Test
	public void testGetCountOk() {
		when(stockRepository.count()).thenReturn(Mono.just(50L));
		
		Mono<Long> response = productService.count();
	
		assertNotNull(response);
		assertNotNull(response.block());
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetCountFali() {
		when(stockRepository.count()).thenThrow(new RuntimeException());
		
		productService.count();
	}
	
	@Test(expected = BusinessException.class)
	public void testDeleteNotFoundFail() {
		when(stockRepository.findFirstById(anyString())).thenThrow(new BusinessException());
		
		productService.delete("5e386bb0462d9d5c0ff62848");
	}
	
	@Test(expected = BusinessException.class)
	public void testDeleteFail() {
		
		when(stockRepository.findFirstById(anyString())).thenReturn(Mono.just(stock));
		when(stockRepository.delete(any())).thenThrow(new BusinessException());
		
		productService.delete("5e386bb0462d9d5c0ff62848");
	}
}
