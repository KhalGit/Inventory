package com.gkalogeroudis.springexercise.demo.validation;

import com.gkalogeroudis.springexercise.demo.exception.EmptyStringException;
import com.gkalogeroudis.springexercise.demo.exception.NotFoundIdException;
import com.gkalogeroudis.springexercise.demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryValidator {

    @Autowired
    InventoryRepository inventoryRepository;

    public void descriptionEmpty(String description)    {
        if (description == null || description == "") {
            throw new EmptyStringException(description);
        }
    }

    public void idNotFound(Long id) {
        if (inventoryRepository.findById(id).isEmpty())   {
            throw new NotFoundIdException(id);
        }
    }
}
