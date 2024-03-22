package com.gkalogeroudis.springexercise.demo.repository;

import com.gkalogeroudis.springexercise.demo.entity.StockHistory;
import com.gkalogeroudis.springexercise.demo.repository.custom.StockHistoryCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long>, StockHistoryCustomRepository {


}
