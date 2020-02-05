package com.todo1.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.todo1.api.exception.BusinessException;
import com.todo1.api.exception.ErrorMessage;
import com.todo1.api.model.Stock;
import com.todo1.api.repository.StockRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockService implements StockBaseService<Stock> {

	@Autowired
	private StockRepository stockRepository;

	@Override
	public Mono<Stock> create(Mono<Stock> stock) {
		return stockRepository.save(stock.block());
	}

	@Override
	public Mono<Stock> update(Mono<Stock> stock, String id) {
		// Check if exists stock with this id
		Stock stockResource = getOneById(id).block();
		Stock st = stock.block();
		st.setId(stockResource.getId());
		// update stock resource
		return stockRepository.save(st);
	}

	@Override
	public Mono<Stock> addStock(String id, Long count) {
		Stock stock = getOneById(id).block();
		
		if (count < 0) {
			ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, "La cantidad de Stock no puede ser negativo");
			throw new BusinessException(errorMessage);
		}
		
		stock.setCount(stock.getCount() + count);
		return stockRepository.save(stock);
	}
	
	@Override
	public Mono<Stock> removeStock(String id, Long count) {
		Stock stock = getOneById(id).block();
		
		if (count < 0) {
			ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, "La cantidad de Stock no puede ser negativo");
			throw new BusinessException(errorMessage);
		}
		
		// check if there is enough stock
		if (stock.getCount() < count) {
			ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, "No hay suficiente Stock para esta operación");
			throw new BusinessException(errorMessage);
		}
		stock.setCount(stock.getCount() - count);
		return stockRepository.save(stock);
	}
	
	@Override
	public Mono<Stock> getOneById(String id) {
		Stock stock = stockRepository.findFirstById(id).block();
		if (stock == null) {
			ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, "Stock no encontrado");
			throw new BusinessException(errorMessage);
		}
		return Mono.just(stock);
	}

	@Override
	public Flux<Stock> getAll(int page, int size) {
		Pageable pag = PageRequest.of(page, size);
		return stockRepository.findAllBy(pag);
	}
	
    @Override
    public Mono<Long> count() {
        return stockRepository.count();
    }
    
    @Override
    public Mono<Void> delete(String id) {
    	Stock stock = getOneById(id).block();
        return stockRepository.delete(stock);
    }

}
