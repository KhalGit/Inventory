package com.gkalogeroudis.springexercise.demo.validation;

import com.gkalogeroudis.springexercise.demo.entity.ImpExport;
import com.gkalogeroudis.springexercise.demo.exception.EmptyStringException;
import com.gkalogeroudis.springexercise.demo.exception.NotFoundIdException;
import com.gkalogeroudis.springexercise.demo.exception.NullEnumException;
import com.gkalogeroudis.springexercise.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {

    @Autowired
    TransactionRepository transactionRepository;

    public void impExportNull(ImpExport measure)  {
        if (measure == null || (measure != ImpExport.INPUT && measure != ImpExport.OUTPUT)) {
            throw new NullEnumException(measure);
        }
    }

    public void descriptionEmpty(String description)   {
        if (description == null || description == "") {
            throw new EmptyStringException(description);
        }
    }

    public void transportationEmpty(String transportationReceive, String transportationDeliver)   {
        if (transportationReceive == "" && transportationDeliver == "") {
            throw new EmptyStringException(transportationReceive + ", " + transportationDeliver);
        }
    }

    public void idNotFound(Long id) {
        if (transactionRepository.findById(id).isEmpty())   {
            throw new NotFoundIdException(id);
        }
    }
}
