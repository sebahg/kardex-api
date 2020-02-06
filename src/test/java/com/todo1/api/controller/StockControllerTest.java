package com.todo1.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.todo1.api.controller.dto.ProductDto;
import com.todo1.api.controller.dto.StockDto;
import com.todo1.api.converter.StockConverter;
import com.todo1.api.model.Stock;
import com.todo1.api.service.StockService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockControllerTest {

	@InjectMocks
	private StockController stockController;
	
	@Mock
	private StockService stockService;
	
	@Spy
	private StockConverter stockConverter;
	
	private Stock stock;
	
	@Before
	public void setup() {
		stock = new Stock();
		stock.setCount(20L);
		stock.setId("5e386bb0462d9d5c0ff62848");
		stock.setProductId("5e386bb0462d9d5c0ff62848");
	}
	
	@Test
	public void testGetAllOk() {
		when(stockService.getAll(anyInt(), anyInt())).thenReturn(Flux.just(stock));
		
		Flux<StockDto> response = stockController.getAll(0, 10);
		
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetAllFail() {
		when(stockService.getAll(anyInt(), anyInt())).thenThrow(new RuntimeException());
		
		stockController.getAll(0, 10);
	}
	
	@Test
	public void testGetCountOk() {
		when(stockService.count()).thenReturn(Mono.just(10L));
		
		Mono<Long> response = stockController.getCount();
		
		assertNotNull(response);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetCountFail() {
		when(stockService.count()).thenThrow(new RuntimeException());
		
		stockController.getCount();

	}
	
	@Test
	public void testGetOneByIdOk() {
		when(stockService.getOneById(anyString())).thenReturn(Mono.just(stock));
		
		Mono<StockDto> response = stockController.getOneById("5e386bb0462d9d5c0ff62848");
		
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOneByIdFail() {
		when(stockService.getOneById(anyString())).thenThrow(new RuntimeException());
		
		stockController.getOneById("5e386bb0462d9d5c0ff62848");
		
	}
	
	@Test
	public void testCreateOk() {
		when(stockService.create(any())).thenReturn(Mono.just(stock));
		
		ProductDto product = new ProductDto();
		product.setId("5e386bb0462d9d5c0ff62848");
		product.setName("Accesorio");
		
		StockDto stockDto = new StockDto();
		stockDto.setCount(10L);
		stockDto.setId("5e386bb0462d9d5c0ff62848");
		stockDto.setProduct(product);
		
		Mono<StockDto> response = stockController.create(stockDto);
		
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateFail() {
		when(stockService.create(any())).thenThrow(new RuntimeException());
		
		ProductDto product = new ProductDto();
		product.setId("5e386bb0462d9d5c0ff62848");
		product.setName("Accesorio");
		
		StockDto stockDto = new StockDto();
		stockDto.setCount(10L);
		stockDto.setId("5e386bb0462d9d5c0ff62848");
		stockDto.setProduct(product);
		
		stockController.create(stockDto);

	}
	
	@Test
	public void testUpdateOk() {
		when(stockService.update(any(), anyString())).thenReturn(Mono.just(stock));
		
		ProductDto product = new ProductDto();
		product.setId("5e386bb0462d9d5c0ff62848");
		product.setName("Accesorio");
		
		StockDto stockDto = new StockDto();
		stockDto.setCount(10L);
		stockDto.setId("5e386bb0462d9d5c0ff62848");
		stockDto.setProduct(product);
		
		Mono<StockDto> response = stockController.update("5e386bb0462d9d5c0ff62848", stockDto);
		
		assertNotNull(response);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateFail() {
		when(stockService.update(any(), anyString())).thenThrow(new RuntimeException());
		
		ProductDto product = new ProductDto();
		product.setId("5e386bb0462d9d5c0ff62848");
		product.setName("Accesorio");
		
		StockDto stockDto = new StockDto();
		stockDto.setCount(10L);
		stockDto.setId("5e386bb0462d9d5c0ff62848");
		stockDto.setProduct(product);
		
		stockController.update("5e386bb0462d9d5c0ff62848", stockDto);

	}
}
