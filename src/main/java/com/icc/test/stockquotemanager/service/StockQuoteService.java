package com.icc.test.stockquotemanager.service;

import com.icc.test.stockquotemanager.entity.Quote;
import com.icc.test.stockquotemanager.entity.StockQuote;
import com.icc.test.stockquotemanager.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StockQuoteService {

    @Autowired
    StockRepository stockRepository;
    public Iterable<StockQuote> getAll(){
        return stockRepository.findAll();
    }

    RestTemplate restTemplate = new RestTemplate();
    List<StockQuote> stockCache = new ArrayList<>();

    public List<StockQuote> getStocks() {
        ResponseEntity<List<StockQuote>> stocks = restTemplate.exchange("http://192.168.99.100:8080/stock", HttpMethod.GET, null, new ParameterizedTypeReference<List<StockQuote>>() {}, Collections.emptyMap() ) ;
        List<StockQuote> stocksList = stocks.getBody();
        return stocksList;
    }

    public void clearCache() {
        stockCache = null;
    }

    public boolean isValidStockQuote(StockQuote stockQuote) {
        List<StockQuote> existingStocks = null;
        if (stockCache == null){
            existingStocks = getStocks();
            stockCache = existingStocks;
        } else {
            existingStocks = stockCache;
        }
        existingStocks = getStocks();
        for (StockQuote stock  : existingStocks){
            if(stock.getId().equals(stockQuote)){
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<StockQuote> saveStockQuote(StockQuote stockQuote) throws RuntimeException {
        if(!isValidStockQuote(stockQuote)) {
            return ResponseEntity.notFound().build();
        }
        Optional<StockQuote> existingStock = stockRepository.findById(stockQuote.getId());
        if (existingStock.isPresent()) {
            List<Quote> newQuotes = new ArrayList<Quote>();

            for (Quote quote : stockQuote.getQuotes()) {
                for (Quote existingQuote : existingStock.get().getQuotes()) {
                    if (!quote.getDate().equals(existingQuote.getDate())) {
                        newQuotes.add(quote);
                    }
                }
            }
            stockQuote.setQuotes(newQuotes);
            stockRepository.save(stockQuote);

            return ResponseEntity.ok().build();

        } else {
            stockRepository.save(stockQuote);
            return ResponseEntity.noContent().build();
        }

    }

    public StockQuote getById(String id){
        Optional<StockQuote> optionalStock = stockRepository.findById(id);
        return optionalStock.orElseThrow(() -> new RuntimeException("Stock not found"));
    }
}
