package com.gkalogeroudis.springexercise.demo.validation;

import com.gkalogeroudis.springexercise.demo.exception.EmptyStringException;
import com.gkalogeroudis.springexercise.demo.exception.NotFoundIdException;
import com.gkalogeroudis.springexercise.demo.exception.NullEntityException;
import com.gkalogeroudis.springexercise.demo.repository.InventoryRepository;
import com.gkalogeroudis.springexercise.demo.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShelfValidator {

    @Autowired
    ShelfRepository shelfRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    public void inventoryNull(Long id)  {
        if (!inventoryRepository.existsById(id)) {
            throw new NullEntityException(id);
        }
    }

    public void barcodeEmpty(String barcode)    {
        if (barcode == null || barcode == "")   {
            throw new EmptyStringException(barcode);
        }
    }

    public void idNotFound(Long id) {
        if (shelfRepository.findById(id).isEmpty())   {
            throw new NotFoundIdException(id);
        }
    }
}
