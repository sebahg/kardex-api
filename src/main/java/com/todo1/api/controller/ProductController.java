package com.todo1.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo1.api.controller.dto.ProductDto;
import com.todo1.api.converter.ProductConverter;
import com.todo1.api.model.Product;
import com.todo1.api.service.BaseService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("products")
public class ProductController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductConverter productConverter;
	
	@Autowired
	private BaseService<Product> productService;
	
	/**
	 * Get all products.
	 * @param page index.
	 * @param size of the page.
	 * @return list of products of the page.
	 */
	@GetMapping()
    public Flux<ProductDto> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        LOGGER.info("getAll: " + "page=" + page + ", size=" + size);
        return productService.getAll(page, size)
        		.flatMap(product -> productConverter.convertFromResourceToDto(Mono.just(product)));
    }

	/**
	 * Get total count of products.
	 * @return
	 */
    @GetMapping("/count")
    public Mono<Long> getCount() {
        LOGGER.info("getCount");
        return productService.count();
    }

    /**
     * Get one product by id.
     * @param id of the product.
     * @return product
     */
    @GetMapping("/{id}")
    public Mono<ProductDto> getOneById(@PathVariable String id) {

        LOGGER.info("getOneById: " + "id=" + id);
        return productService.getOneById(id)
        		.flatMap(product -> productConverter.convertFromResourceToDto(Mono.just(product)));
    }

    /**
     * Create new product.
     * @param productDto product to create.
     * @return product created.
     */
    @PostMapping()
    public Mono<ProductDto> create(@RequestBody ProductDto productDto) {

        LOGGER.info("create: " + "body=" + productDto);
        Mono<Product> prodType = productConverter.convertFromDtoToResource(Mono.just(productDto));
        return productService.create(prodType)
        		.flatMap(product -> productConverter.convertFromResourceToDto(Mono.just(product)));
    }

    /**
     * Update an existing product.
     * @param id of the product.
     * @param productDto product to update.
     * @return product updated.
     */
    @PutMapping("/{id}")
    public Mono<ProductDto> update(
            @PathVariable String id,
            @RequestBody ProductDto productDto) {

        LOGGER.info("update: " + "id=" + id + ", body=" + productDto);
        Mono<Product> prodType = productConverter.convertFromDtoToResource(Mono.just(productDto));
        return productService.update(prodType, id)
        		.flatMap(product -> productConverter.convertFromResourceToDto(Mono.just(product)));
    }
    
    /**
     * Delete product by id.
     * @param id of product.
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(
            @PathVariable String id) {

        LOGGER.info("delete: " + "id=" + id);
        return productService.delete(id);
    }
}
