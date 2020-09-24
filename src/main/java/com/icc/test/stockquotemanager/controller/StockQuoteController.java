package com.icc.test.stockquotemanager.controller;

import com.icc.test.stockquotemanager.entity.Quote;
import com.icc.test.stockquotemanager.entity.StockQuote;
import com.icc.test.stockquotemanager.service.QuoteService;
import com.icc.test.stockquotemanager.service.StockQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/quote", consumes = MediaType.APPLICATION_JSON_VALUE)
public class StockQuoteController {

    @Autowired
    QuoteService quoteService;

    @Autowired
    StockQuoteService stockQuoteService;

    @GetMapping
    public Iterable<Quote> getAll(){
        return quoteService.getAll();
    }

    @PutMapping
    public ResponseEntity<StockQuote> createQuote(@RequestBody StockQuote stockQuote) {
         return stockQuoteService.saveStockQuote(stockQuote);
    }

    @GetMapping("/{id}")
    public Quote getById(Integer id) {
        return quoteService.getById(id);
    }


}
