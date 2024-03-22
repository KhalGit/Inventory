package com.gkalogeroudis.springexercise.demo.validation;

import com.gkalogeroudis.springexercise.demo.exception.NegativeValueException;
import com.gkalogeroudis.springexercise.demo.exception.NotFoundIdException;
import com.gkalogeroudis.springexercise.demo.exception.NullEntityException;
import com.gkalogeroudis.springexercise.demo.repository.ProductRepository;
import com.gkalogeroudis.springexercise.demo.repository.ShelfRepository;
import com.gkalogeroudis.springexercise.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StockValidator {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    ShelfRepository shelfRepository;
    @Autowired
    ProductRepository productRepository;

    public void stockQuantityNegative(BigDecimal quantity)   {
        if (quantity.compareTo(BigDecimal.valueOf(0)) < 0)  {
            throw new NegativeValueException(quantity.toString());
        }
    }

    public void shelfNull(Long id)  {
        if (!shelfRepository.existsById(id)) {
            throw new NullEntityException(id);
        }
    }

    public void productNull(Long id)  {
        if (!productRepository.existsById(id)) {
            throw new NullEntityException(id);
        }
    }

    public void idNotFound(Long id) {
        if (stockRepository.findById(id).isEmpty())   {
            throw new NotFoundIdException(id);
        }
    }
}
