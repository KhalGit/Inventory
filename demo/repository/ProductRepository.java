package com.gkalogeroudis.springexercise.demo.repository;

import com.gkalogeroudis.springexercise.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
