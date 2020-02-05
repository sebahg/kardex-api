package com.todo1.api.converter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo1.api.controller.dto.StockDto;
import com.todo1.api.model.Stock;
import com.todo1.api.model.Product;
import com.todo1.api.service.BaseService;

import reactor.core.publisher.Mono;

@Component
public class StockConverter {
	
	@Autowired
	private BaseService<Product> productService;
	
	@Autowired
	private ProductConverter productConverter;
	
	/**
	 * Convert a resource to a dto model.
	 * @param stock to convert.
	 * @return dto converted.
	 */
	public Mono<StockDto> convertFromResourceToDto(Mono<Stock> stock) {
		return stock.flatMap(stockResource -> {
			StockDto response = new StockDto();
			response.setId(stockResource.getId());
			response.setCount(stockResource.getCount());
			Optional.ofNullable(productService.getOneById(stockResource.getProductId()).block())
				.ifPresent(product -> response.setProduct(productConverter.convertFromResourceToDto(Mono.just(product)).block()));
			return Mono.just(response);
		});
	}
	
	/**
	 * Convert a dto to a resource.
	 * @param productDto dto to convert.
	 * @return resource converted.
	 */
	public Mono<Stock> convertFromDtoToResource(Mono<StockDto> productDto) {
		return productDto.flatMap(prodDto -> {
			Stock response = new Stock();
			response.setId(prodDto.getId());
			response.setCount(prodDto.getCount());
			Optional.ofNullable(prodDto.getProduct())
				.ifPresent(product -> response.setProductId(product.getId()));
			return Mono.just(response);
		});
	}

}
