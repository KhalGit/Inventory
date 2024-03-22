package com.gkalogeroudis.springexercise.demo.repository.custom;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface StockHistoryCustomRepository {

    public List findStockHistory(Date dates, String barcode);

}
