package com.todo1.api.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.todo1.api.controller.dto.ProductDto;
import com.todo1.api.model.Product;

import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ProductConverterTest {

	@InjectMocks
	private ProductConverter productConverter;
	
	@Test
	public void testConvertFromResourceToDtoOk() {
		
		Product product = new Product();
		product.setId("5e386bb0462d9d5c0ff62848");
		product.setName("Juguete");
				
		ProductDto productDto = productConverter.convertFromResourceToDto(Mono.just(product)).block();
		
		assertNotNull(productDto);
		assertEquals(product.getId(), productDto.getId());
		assertEquals(product.getName(), productDto.getName());
		
	}
	
	@Test
	public void testConvertFromDtoToResourceOk() {
		
		ProductDto productDto = new ProductDto();
		productDto.setId("5e386bb0462d9d5c0ff62848");
		productDto.setName("Juguete");
				
		Product product = productConverter.convertFromDtoToResource(Mono.just(productDto)).block();
		
		assertNotNull(product);
		assertEquals(productDto.getId(), product.getId());
		assertEquals(productDto.getName(), product.getName());
		
	}
}
