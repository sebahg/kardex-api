package com.todo1.api.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo1.api.model.Stock;
import com.todo1.api.model.Product;
import com.todo1.api.service.BaseService;

import reactor.core.publisher.Mono;

@Configuration
public class MongoImportConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoImportConfig.class);
	
	@Autowired
    private BaseService<Product> productService;
	
	@Autowired
    private BaseService<Stock> stockService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Import a list of products and stock from resources and add it to the database.
	 * Just to have some information available.
	 */
	@PostConstruct
    public void importDocuments() {
		
		List<Product> productList = null;
		List<Stock> stockList = null;
		try {
			// Get information from resources
			productList = mapper.readValue(new File(getClass().getClassLoader().getResource("json/products.json").getFile()), new TypeReference<List<Product>>() {});
			stockList = mapper.readValue(new File(getClass().getClassLoader().getResource("json/stock.json").getFile()), new TypeReference<List<Stock>>() {});
			
			
			//Save products and stock in database
			productList.stream().forEach(product -> productService.create(Mono.just(product)).subscribe());
			stockList.stream().forEach(stock -> stockService.create(Mono.just(stock)).subscribe());
		} catch (IOException e) {
			LOGGER.info("Information from resources could not be imported to the database", e.getMessage());
		}
		
    }
	
}
