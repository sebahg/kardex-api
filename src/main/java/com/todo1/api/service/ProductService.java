package com.todo1.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.todo1.api.exception.BusinessException;
import com.todo1.api.exception.ErrorMessage;
import com.todo1.api.model.Product;
import com.todo1.api.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements BaseService<Product>{

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Mono<Product> create(Mono<Product> product) {
		return productRepository.save(product.block());
	}

	@Override
	public Mono<Product> update(Mono<Product> product, String id) {
		// Check if exists product with this id
		Product prodResource = getOneById(id).block();
		Product prod = product.block();
		prod.setId(prodResource.getId());
		// update product resource
		return productRepository.save(prod);
	}

	@Override
	public Mono<Product> getOneById(String id) {
		Product product = productRepository.findFirstById(id).block();
		if (product == null) {
			ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, "Product not found");
			throw new BusinessException(errorMessage);
		}
		return Mono.just(product);
	}

	@Override
	public Flux<Product> getAll(int page, int size) {
		Pageable pag = PageRequest.of(page, size);
		return productRepository.findAllBy(pag);
	}
	
    @Override
    public Mono<Long> count() {
        return productRepository.count();
    }
    
    @Override
    public Mono<Void> delete(String id) {
    	Product product = productRepository.findFirstById(id).block();
        return productRepository.delete(product);
    }
    
}
