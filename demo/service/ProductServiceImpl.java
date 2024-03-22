package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.ProductDTO;
import com.gkalogeroudis.springexercise.demo.dto.StockDTO;
import com.gkalogeroudis.springexercise.demo.dto.TransactionDetailsDTO;
import com.gkalogeroudis.springexercise.demo.entity.Product;
import com.gkalogeroudis.springexercise.demo.entity.Stock;
import com.gkalogeroudis.springexercise.demo.entity.TransactionDetails;
import com.gkalogeroudis.springexercise.demo.exception.DataNotFound;
import com.gkalogeroudis.springexercise.demo.repository.ProductRepository;
import com.gkalogeroudis.springexercise.demo.validation.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductValidator productValidator;


	@Override
	public List<ProductDTO> findAllProduct() {
		List<Product> theProducts = productRepository.findAll();
		List<ProductDTO> theProductsDTO = new ArrayList<>();

		for (Product p: theProducts)	{
			theProductsDTO.add(new ProductDTO(p));
		}

		if (theProductsDTO.isEmpty())	{
			DataNotFound dataNotFound;
		}
		return theProductsDTO;
	}

	@Override
	public ProductDTO findByProductId(Long productId) {
		productValidator.idNotFound(productId);
		Product theProduct = productRepository.findById(productId).orElseThrow(ArithmeticException::new);
		return new ProductDTO(theProduct);
	}

	@Override
	public ProductDTO addProduct(ProductDTO theProductDTO) {
		productValidator.productPricesNegative(theProductDTO.getPrice());
		productValidator.barcodeDescriptionEmpty(theProductDTO.getBarcode());
		productValidator.barcodeDescriptionEmpty(theProductDTO.getDescription());
		Product theProduct = new Product(theProductDTO);

		theProduct = productRepository.save(theProduct);
		return toDTO(theProduct);
	}

	@Override
	public void deleteByProductId(Long productId) {
		productValidator.idNotFound(productId);
		productRepository.deleteById(productId);
	}

	@Override
	public ProductDTO updateProduct(ProductDTO theProductDTO, Long productId) {
		productValidator.idNotFound(productId);
		productValidator.productPricesNegative(theProductDTO.getPrice());
		productValidator.barcodeDescriptionEmpty(theProductDTO.getBarcode());
		productValidator.barcodeDescriptionEmpty(theProductDTO.getDescription());
		Product theProduct = productRepository.findById(productId).get();

		if (Objects.nonNull(theProductDTO.getBarcode()) && !"".equalsIgnoreCase(theProductDTO.getBarcode())) {
			theProduct.setBarcode(theProductDTO.getBarcode());
		}


		if (Objects.nonNull(theProductDTO.getDescription()) && !"".equalsIgnoreCase(theProductDTO.getDescription())) {
			theProduct.setDescription(theProductDTO.getDescription());
		}

		if (Objects.nonNull(theProductDTO.getPrice()) && !"".equals(theProductDTO.getPrice())) {
			theProduct.setPrice(theProductDTO.getPrice());
		}

		if (Objects.nonNull(theProductDTO.getMeasure()) && !"".equals(theProductDTO.getMeasure())) {
			theProduct.setMeasure(theProductDTO.getMeasure());
		}

		theProduct = productRepository.save(theProduct);

		return toDTO(theProduct);
	}

	private ProductDTO toDTO(Product product)	{
		ProductDTO dto = new ProductDTO(product);
		if (product.getStocks() != null) {
			for (Stock s : product.getStocks()) {
				dto.getStocks().add(new StockDTO(s));
			}
		}

		return dto;
	}
}