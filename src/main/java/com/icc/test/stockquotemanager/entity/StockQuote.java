package com.icc.test.stockquotemanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.icc.test.stockquotemanager.deserializer.QuoteListDeserializer;
import com.icc.test.stockquotemanager.serializer.QuoteListSerializer;

import javax.persistence.*;
import java.util.List;

@Entity
public class StockQuote {

    @Id
    @Column (name = "id", unique = true, nullable = false)
    String id;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    @JsonSerialize(using = QuoteListSerializer.class)
    @JsonDeserialize(using = QuoteListDeserializer.class)
    public List<Quote> quotes;

    public StockQuote() {
    }

    public StockQuote(String id, List<Quote> quotes) {
        this.id = id;
        this.quotes = quotes;
    }

    public String getId() {
        return id;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
