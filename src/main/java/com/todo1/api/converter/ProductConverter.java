package com.todo1.api.converter;

import org.springframework.stereotype.Component;

import com.todo1.api.controller.dto.ProductDto;
import com.todo1.api.model.Product;

import reactor.core.publisher.Mono;

@Component
public class ProductConverter {

	/**
	 * Convert a resource to a dto model.
	 * @param product resource to convert.
	 * @return dto converted.
	 */
	public Mono<ProductDto> convertFromResourceToDto(Mono<Product> product) {
		return product.flatMap(prod -> {
			ProductDto response = new ProductDto();
			response.setId(prod.getId());
			response.setName(prod.getName());
			return Mono.just(response);
		});
	}
	
	/**
	 * Convert a dto to a resource.
	 * @param productDto dto to convert.
	 * @return resourece converted.
	 */
	public Mono<Product> convertFromDtoToResource(Mono<ProductDto> productDto) {
		return productDto.flatMap(prodDto -> {
			Product response = new Product();
			response.setId(prodDto.getId());
			response.setName(prodDto.getName());
			return  Mono.just(response);
		});
	}
}
