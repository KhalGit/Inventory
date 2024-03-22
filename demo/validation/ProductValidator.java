package com.gkalogeroudis.springexercise.demo.validation;

import com.gkalogeroudis.springexercise.demo.entity.Measure;
import com.gkalogeroudis.springexercise.demo.exception.EmptyStringException;
import com.gkalogeroudis.springexercise.demo.exception.NegativeValueException;
import com.gkalogeroudis.springexercise.demo.exception.NotFoundIdException;
import com.gkalogeroudis.springexercise.demo.exception.NullEnumException;
import com.gkalogeroudis.springexercise.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductValidator {

    @Autowired
    ProductRepository productRepository;

    public void productPricesNegative(BigDecimal price)   {
        if (price.compareTo(BigDecimal.valueOf(0)) < 0)  {
            throw new NegativeValueException(price.toString());
        }
    }

    public void barcodeDescriptionEmpty(String string)   {
        if (string == null || string == "") {
            throw new EmptyStringException(string);
        }
    }

    public void measureNull(Measure measure)  {
        if (measure == null || (measure != Measure.KILOS && measure != Measure.PIECES)) {
            throw new NullEnumException(measure);
        }
    }

    public void idNotFound(Long id) {
        if (productRepository.findById(id).isEmpty())   {
            throw new NotFoundIdException(id);
        }
    }
}
