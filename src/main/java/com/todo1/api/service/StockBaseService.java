package com.todo1.api.service;

import com.todo1.api.model.Stock;

import reactor.core.publisher.Mono;

public interface StockBaseService<T> extends BaseService<T> {

	/**
	 * Add stock of an existing product.
	 * @param id of the existing product.
	 * @param count of stock to add.
	 * @return updated resource.
	 */
	Mono<T> addStock(String id, Long count);

	/**
	 * Remove stock of an existing product.
	 * @param id of the existing product.
	 * @param count of stock to remove.
	 * @return update resource.
	 */
	Mono<Stock> removeStock(String id, Long count);
}
