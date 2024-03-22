package com.gkalogeroudis.springexercise.demo.repository.custom;

import com.gkalogeroudis.springexercise.demo.dto.StockByDateDto;
import com.gkalogeroudis.springexercise.demo.entity.QProduct;
import com.gkalogeroudis.springexercise.demo.entity.QStock;
import com.gkalogeroudis.springexercise.demo.entity.QStockHistory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

public class StockHistoryRepositoryImpl implements StockHistoryCustomRepository {

    private final QStock qStock = QStock.stock;
    private final QStockHistory qStockHistory = QStockHistory.stockHistory;
    private final QProduct qProduct = QProduct.product;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StockByDateDto> findStockHistory(Date dates, String barcode) {

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qProduct.barcode.like(barcode));
        predicate.and(qStockHistory.dates.goe(dates));

        JPAQuery<StockByDateDto> query = new JPAQuery<>(entityManager);
        query.select(stockHistoryProjection())
                .from(qStockHistory)
                .innerJoin(qStock).on(qStockHistory.stock.id.eq(qStock.id))
                .innerJoin(qProduct).on(qStock.product.id.eq(qProduct.id))
                .where(predicate);

        List<StockByDateDto> s = query.fetch();
        return query.fetch();
    }

    private FactoryExpression<StockByDateDto> stockHistoryProjection(){

        return Projections.bean(StockByDateDto.class,
                qStock.shelf.id.as("shelfId"),
                qStockHistory.quantity.as("quantity"),
                qProduct.barcode.as("barcode"),
                qStockHistory.dates.as("dates")
        );
    }
}