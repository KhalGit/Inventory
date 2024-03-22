package com.gkalogeroudis.springexercise.demo.repository;

import com.gkalogeroudis.springexercise.demo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
