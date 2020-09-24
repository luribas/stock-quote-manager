package com.icc.test.stockquotemanager.service;

import com.icc.test.stockquotemanager.entity.Quote;
import com.icc.test.stockquotemanager.entity.StockQuote;
import com.icc.test.stockquotemanager.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuoteService {

    @Autowired
    StockQuoteService stockQuoteService;

    @Autowired
    QuoteRepository quoteRepository;

    public Iterable<Quote> getAll() {
        return quoteRepository.findAll();
    }

    public Quote createQuote(Quote quote, String stockId) {
        StockQuote stockQuote = stockQuoteService.getById(stockId);
        Quote created = quoteRepository.save(quote);
        return created;
    }

    public Quote getById(int id){
        Optional<Quote> optionalQuote = quoteRepository.findById(id);
        return optionalQuote.orElseThrow(() -> new RuntimeException("Quotation not found"));
    }

}
