package com.gkalogeroudis.springexercise.demo.validation;

import com.gkalogeroudis.springexercise.demo.exception.NegativeValueException;
import com.gkalogeroudis.springexercise.demo.exception.NotFoundIdException;
import com.gkalogeroudis.springexercise.demo.exception.NullEntityException;
import com.gkalogeroudis.springexercise.demo.repository.StockHistoryRepository;
import com.gkalogeroudis.springexercise.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StockHistoryValidator {

    @Autowired
    StockHistoryRepository stockHistoryRepository;
    @Autowired
    StockRepository stockRepository;

    public void stockHistoryQuantityNegative(BigDecimal quantity)   {
        if (quantity.compareTo(BigDecimal.valueOf(0)) < 0)  {
            throw new NegativeValueException(quantity.toString());
        }
    }

    public void stockNull(Long id)  {
        if (!stockRepository.existsById(id)) {
            throw new NullEntityException(id);
        }
    }

    public void idNotFound(Long id) {
        if (stockHistoryRepository.findById(id).isEmpty())   {
            throw new NotFoundIdException(id);
        }
    }
}
