package com.todo1.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.todo1.api.controller.dto.StockDto;
import com.todo1.api.converter.StockConverter;
import com.todo1.api.model.Stock;
import com.todo1.api.service.StockBaseService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stock")
public class StockController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	private StockConverter stockConverter;
	
	@Autowired
	private StockBaseService<Stock> stockService;
	
	/**
	 * Get all stock of products.
	 * @param page index.
	 * @param size of the page.
	 * @return list of stock of the page.
	 */
	@GetMapping()
    public Flux<StockDto> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        LOGGER.info("getAll: " + "page=" + page + ", size=" + size);
        return stockService.getAll(page, size)
        		.flatMap(product -> stockConverter.convertFromResourceToDto(Mono.just(product)));
    }

	/**
	 * Get total count of stock.
	 * @return
	 */
    @GetMapping("/count")
    public Mono<Long> getCount() {
        LOGGER.info("getCount");
        return stockService.count();
    }

    /**
     * Get one stock by id.
     * @param id of the stock.
     * @return stock.
     */
    @GetMapping("/{id}")
    public Mono<StockDto> getOneById(@PathVariable String id) {

        LOGGER.info("getOneById: " + "id=" + id);
        return stockService.getOneById(id)
        		.flatMap(stock -> stockConverter.convertFromResourceToDto(Mono.just(stock)));
    }

    /**
     * Create new stock of a product.
     * @param stockDto stock to create.
     * @return stock created.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<StockDto> create(@RequestBody StockDto stockDto) {

        LOGGER.info("create: " + "body=" + stockDto);
        Mono<Stock> stockResource = stockConverter.convertFromDtoToResource(Mono.just(stockDto));
        return stockService.create(stockResource)
        		.flatMap(stock -> stockConverter.convertFromResourceToDto(Mono.just(stock)));
    }

    /**
     * Update an existing stock of a product.
     * @param id of stock.
     * @param stockDto stock to update.
     * @return updated stock.
     */
    @PutMapping("/{id}")
    public Mono<StockDto> update(
            @PathVariable String id,
            @RequestBody StockDto stockDto) {

        LOGGER.info("update: " + "id=" + id + ", body=" + stockDto);
        Mono<Stock> stockResource = stockConverter.convertFromDtoToResource(Mono.just(stockDto));
        return stockService.update(stockResource, id)
        		.flatMap(stock -> stockConverter.convertFromResourceToDto(Mono.just(stock)));
    }
    
    /**
     * Add stock of a product.
     * @param id of the stock.
     * @param count of stock to add.
     * @return
     */
    @PutMapping("/{id}/add")
    public Mono<StockDto> addStock(
            @PathVariable String id,
            @RequestParam Long count) {

        LOGGER.info("addStock: " + "id=" + id + ", " + " count=" + count);
        return stockService.addStock(id, count)
        		.flatMap(stock -> stockConverter.convertFromResourceToDto(Mono.just(stock)));
    }
    
    /**
     * Remove stock of a product.
     * @param id of the stock.
     * @param count of stock to remove.
     * @return
     */
    @PutMapping("/{id}/remove")
    public Mono<StockDto> removeStock(
            @PathVariable String id,
            @RequestParam Long count) {

        LOGGER.info("removeStock: " + "id=" + id + ", " + " count=" + count);
        return stockService.removeStock(id, count)
        		.flatMap(stock -> stockConverter.convertFromResourceToDto(Mono.just(stock)));
    }
    
    /**
     * Delete stock by id.
     * @param id of the stock.
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(
            @PathVariable String id) {

        LOGGER.info("delete: " + "id=" + id);
        return stockService.delete(id);
    }
}
