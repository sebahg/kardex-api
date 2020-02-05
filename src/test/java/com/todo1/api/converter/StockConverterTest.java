package com.todo1.api.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.todo1.api.controller.dto.ProductDto;
import com.todo1.api.controller.dto.StockDto;
import com.todo1.api.model.Product;
import com.todo1.api.model.Stock;
import com.todo1.api.service.ProductService;

import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockConverterTest {

	@InjectMocks
	private StockConverter stockConverter;
	
	@Mock
	private ProductService productService;
	
	@Mock
	private ProductConverter productConverter; 
	
	@Test
	public void testConvertFromResourceToDtoOk() {
		
		Stock stock = new Stock();
		stock.setId("5e386bb0462d9d5c0ff62848");
		stock.setCount(20L);
		stock.setProductId("5e386bb0462d9d5c0ff62846");
				
		Product product = new Product();
		product.setId("5e386bb0462d9d5c0ff62846");
		product.setName("Juguete");
		
		ProductDto productDto = new ProductDto();
		productDto.setId("5e386bb0462d9d5c0ff62846");
		productDto.setName("Juguete");
		
		when(productService.getOneById(anyString())).thenReturn(Mono.just(product));
		when(productConverter.convertFromResourceToDto(any())).thenReturn(Mono.just(productDto));
		StockDto stockDto = stockConverter.convertFromResourceToDto(Mono.just(stock)).block();
		
		assertNotNull(stockDto);
		assertEquals(stock.getId(), stockDto.getId());
		assertEquals(stock.getCount(), stockDto.getCount());
		assertEquals(stock.getProductId(), stockDto.getProduct().getId());
		
	}
	
	@Test
	public void testConvertFromDtoToResourceOk() {
		
		Product product = new Product();
		product.setId("5e386bb0462d9d5c0ff62846");
		product.setName("Juguete");
		
		ProductDto productDto = new ProductDto();
		productDto.setId("5e386bb0462d9d5c0ff62846");
		productDto.setName("Juguete");
		
		StockDto stockDto = new StockDto();
		stockDto.setId("5e386bb0462d9d5c0ff62848");
		stockDto.setCount(20L);
		stockDto.setProduct(productDto);
				
		Stock stock = stockConverter.convertFromDtoToResource(Mono.just(stockDto)).block();
		
		assertNotNull(stock);
		assertEquals(stockDto.getId(), stock.getId());
		assertEquals(stockDto.getCount(), stock.getCount());
		assertEquals(stockDto.getProduct().getId(), stock.getProductId());
		
	}
	
}
