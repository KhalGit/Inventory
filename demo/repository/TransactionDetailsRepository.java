package com.gkalogeroudis.springexercise.demo.repository;

import com.gkalogeroudis.springexercise.demo.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
//    List<TransactionDetails> findAllByPid(Long pid);
}
