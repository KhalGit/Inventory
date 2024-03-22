package com.gkalogeroudis.springexercise.demo.rest;

import com.gkalogeroudis.springexercise.demo.dto.ShelfDTO;
import com.gkalogeroudis.springexercise.demo.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class ShelfRestController {

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/shelf")
    public List<ShelfDTO> findAllShelf() {
        return shelfService.findAllShelf();
    }

    @GetMapping("/shelf/{id}")
    public ShelfDTO getShelf(@PathVariable Long id) {

        ShelfDTO theShelfDTO = shelfService.findByShelfId(id);

        return theShelfDTO;
    }

    @PutMapping("/shelf/{id}")
    public ShelfDTO updateShelf(@RequestBody ShelfDTO theShelfDTO, @PathVariable("id") Long shelfId) {

        return shelfService.updateShelf(theShelfDTO, shelfId);
    }

    @PostMapping("/shelf")
    public ShelfDTO addShelf(@Validated @RequestBody ShelfDTO theShelfDTO) {
        theShelfDTO.setId(null);
        return shelfService.addShelf(theShelfDTO);

    }

    @DeleteMapping("/shelf/{id}")
    public String deleteShelf(@PathVariable("id") Long shelfId) {

        ShelfDTO tempShelfDTO = shelfService.findByShelfId(shelfId);

        if (tempShelfDTO == null) {
            throw new RuntimeException("Shelf shelfId not found - " + shelfId);
        }

        shelfService.deleteByShelfId(shelfId);

        return "Deleted shelf shelfId - " + shelfId;
    }

}

