package com.icc.test.stockquotemanager.repository;

import com.icc.test.stockquotemanager.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {
}
