package com.gkalogeroudis.springexercise.demo.rest;

import com.gkalogeroudis.springexercise.demo.dto.ProductDTO;
import com.gkalogeroudis.springexercise.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class ProductRestController {

    @Autowired
    private ProductService productService;


    @GetMapping("/product")
    public List<ProductDTO> findAllProduct()   {
        return productService.findAllProduct();
    }

    @GetMapping("/product/{id}")
    public ProductDTO getProduct(@PathVariable Long id)  {
        ProductDTO theProductDTO = productService.findByProductId(id);

        return theProductDTO;
    }

    @PutMapping("/product/{id}")
    public ProductDTO updateProduct(@RequestBody ProductDTO theProductDTO, @PathVariable("id") Long productId)   {

        return productService.updateProduct(theProductDTO, productId);
    }

    @PostMapping("/product")
    public ProductDTO addProduct(@Validated @RequestBody ProductDTO theProductDTO)  {
        theProductDTO.setId(null);

        return productService.addProduct(theProductDTO);

    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable("id") Long productId)    {

        ProductDTO tempProductDTO = productService.findByProductId(productId);

        if (tempProductDTO == null)    {
            throw new RuntimeException("Product id not found - " + productId);
        }

        productService.deleteByProductId(productId);

        return "Deleted product id - " + productId;
    }

}
