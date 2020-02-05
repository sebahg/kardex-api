package com.todo1.api.repository;

import org.springframework.stereotype.Repository;
import com.todo1.api.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

	Mono<Product> save(Product product);
	
	Mono<Product> findFirstById(String id);

    Flux<Product> findAllBy(Pageable page);
}
