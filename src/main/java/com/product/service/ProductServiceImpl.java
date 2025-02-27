package com.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import com.product.exception.ProductCategoryNotMatchException;
import com.product.exception.ProductNotFoundException;
import com.product.repo.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	Product product = new Product();

	List<Product> productList = new ArrayList<>();

	ProductDTO productDTO = new ProductDTO();

	@Override
	public boolean saveProductToDB(ProductDTO productDTO) {

		BeanUtils.copyProperties(productDTO, product);
		Product savedPrdocut = productRepository.save(product);
        
		if( savedPrdocut.getId() != null) 
			return true;
		
		return false;
		
	}

	@Override
	public List<ProductDTO> getAllProductFromDB() {

		List<ProductDTO> productDTOList = new ArrayList<>();
		productList = productRepository.findAll();

		productList.forEach(x -> {

			productDTO = new ProductDTO();
			BeanUtils.copyProperties(x, productDTO);
			productDTOList.add(productDTO);

		});

		return productDTOList;
	}

	@Override
	public List<ProductDTO> getProductByName(String productName) {
		List<ProductDTO> productDTOList = new ArrayList<>();
		try {

			productList = productRepository.findByProductName(productName);

		} catch (ProductNotFoundException e) {
			e.getMessage();
		}

		productList.forEach(x -> {

			productDTO = new ProductDTO();
			BeanUtils.copyProperties(x, productDTO);
			productDTOList.add(productDTO);

		});
		return productDTOList;

	}

	@Override
	public List<ProductDTO> getProductByCategory(String categoryName) {
		List<ProductDTO> productDTOList = new ArrayList<>();
		try {

			productList = productRepository.findByProductCategory(categoryName);

		} catch (ProductCategoryNotMatchException e) {
			e.getMessage();
		}

		productList.forEach(x -> {

			productDTO = new ProductDTO();
			BeanUtils.copyProperties(x, productDTO);

			productDTOList.add(productDTO);
		});

		return productDTOList;
	}

	@Override
	public boolean updateProductDetails(ProductDTO productDTO) {

		Optional<Product> productInfo = productRepository.findById(productDTO.getId());

		if (productInfo.isPresent()) {

			BeanUtils.copyProperties(productDTO, productInfo);
			Product savedProduct = productRepository.save(productInfo.get());
			return savedProduct.getId() != null;
		} else {
			throw new ProductNotFoundException("Product Not Found ");
		}

	}

	@Override
	public boolean deleteProductById(int productId) {

		productRepository.deleteById(productId);
		return true;
	}

}
