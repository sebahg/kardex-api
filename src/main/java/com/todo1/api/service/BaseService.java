package com.todo1.api.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T> {

	/**
	 * Create a new resource.
	 * @param t new resource.
	 * @return new resource created.
	 */
	Mono<T> create(Mono<T> t);
	
	/**
	 * Update an existing resource.
	 * @param t existing resource.
	 * @param id of the existing resource.
	 * @return updated resource.
	 */
	Mono<T> update(Mono<T> t, String id);
	
	/**
	 * Get one resource by id.
	 * @param id of the resource.
	 * @return resource.
	 */
	Mono<T> getOneById(String id);
	
	/**
	 * Get all resources of a page.
	 * @param page index.
	 * @param size of the page.
	 * @return all resources of the page.
	 */
	Flux<T> getAll(int page, int size);
	
	/**
	 * Get total count of resources.
	 * @return total count of resources.
	 */
	Mono<Long> count();
	
	/**
	 * Delete an existing resource.
	 * @param id of the resource.
	 * @return void.
	 */
	Mono<Void> delete(String id);
}
