package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.ProductDTO;

import java.util.List;

public interface ProductService {

	public List<ProductDTO> findAllProduct();

	public ProductDTO findByProductId(Long theProductId);
	
	public ProductDTO addProduct(ProductDTO theProductDTO);
	
	public void deleteByProductId(Long theProductId);

	public ProductDTO updateProduct(ProductDTO productDTO, Long productId);
}
