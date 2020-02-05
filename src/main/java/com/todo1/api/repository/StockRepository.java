package com.todo1.api.repository;

import org.springframework.stereotype.Repository;
import com.todo1.api.model.Stock;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, String> {
	
	Mono<Stock> save(Stock stock);
	
	Mono<Stock> findFirstById(String id);

    Flux<Stock> findAllBy(Pageable page);
}
