package com.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import com.product.exception.ProductCategoryNotMatchException;
import com.product.exception.ProductNotFoundException;
import com.product.repo.ProductRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@SpringBootTest
 class ProductServiceTest {

	@MockitoBean
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@Test
	 void saveProductToDBTest1() {

		ProductDTO dto = new ProductDTO(1, "Mouse", "1000", "Accessories");
		Product product = new Product();
		BeanUtils.copyProperties(dto, product);

		when(productRepository.save(product)).thenReturn(product);

		productService.saveProductToDB(dto);

		assertEquals(true, product.getId() != null);

	}

	@Test
	 void saveProductToDBTest2() {

		ProductDTO dto = new ProductDTO(null, "Mouse", "1000", "Accessories");
		Product product = new Product();
		BeanUtils.copyProperties(dto, product);

		when(productRepository.save(product)).thenReturn(product);

		productService.saveProductToDB(dto);

		assertEquals(false, product.getId() != null);

	}

	@Test
	 void getAllProductFromDBTest() {

		List<ProductDTO> productDTOList = new ArrayList<>();
		List<Product> productList = new ArrayList<>();
		Product p1 = new Product();
		p1.setId(1);
		p1.setProductName("Mobile");
		p1.setProductCategory("Electronics");
		p1.setPrice("5000");
		productList.add(p1);

		when(productRepository.findAll()).thenReturn(productList);
		List<ProductDTO> productDTOLists = productService.getAllProductFromDB();

		productList.forEach(x -> {

			ProductDTO dto = new ProductDTO();
			BeanUtils.copyProperties(x, dto);
			productDTOList.add(dto);
		});

		assertEquals(1, productDTOList.size());
	}

	@Test
	 void getProductByName() {

		List<Product> productList = new ArrayList<>();
		Product p1 = new Product();
		p1.setId(1);
		p1.setProductName("Mobile");
		p1.setProductCategory("Electronics");
		p1.setPrice("5000");
		productList.add(p1);
		when(productRepository.findByProductName("mobile")).thenReturn(productList);
		List<ProductDTO> productDTOList = productService.getProductByName("mobile");

		assertEquals(1, productDTOList.size());
	}

	@Test
	void getProductByNameExceptionTest() {

		List<Product> productList = new ArrayList<>();
		Product p1 = new Product();
		p1.setId(1);
		p1.setProductName("Mobile");
		p1.setProductCategory("Electronics");
		p1.setPrice("5000");
		productList.add(p1);
		OngoingStubbing<List<Product>> except = when(productRepository.findByProductName("mobile"))
				.thenThrow(new ProductNotFoundException("Product Not Found"));
		List<ProductDTO> productDTOList = productService.getProductByName("mobile");

		
	}

	@Test
	 void getProductByCategory() {

		List<Product> productList = new ArrayList<>();
		Product p1 = new Product();
		p1.setId(1);
		p1.setProductName("Mobile");
		p1.setProductCategory("Electronics");
		p1.setPrice("5000");
		productList.add(p1);
		when(productRepository.findByProductCategory("electronics")).thenReturn(productList);
		List<ProductDTO> productDTOList = productService.getProductByCategory("electronics");
		assertEquals(1, productDTOList.size());
	}

	@Test
	 void getProductByCategoryExceptionTest() {

		List<Product> productList = new ArrayList<>();
		Product p1 = new Product();
		p1.setId(1);
		p1.setProductName("Mobile");
		p1.setProductCategory("Electronics");
		p1.setPrice("5000");
		productList.add(p1);
		OngoingStubbing<List<Product>> except = when(productRepository.findByProductCategory("electronics"))
				.thenThrow(new ProductCategoryNotMatchException("Category Not Found"));
		List<ProductDTO> productDTOList = productService.getProductByCategory("electronics");

		
	}

	@Test
	 void updateProductDetails() {
		Product p = new Product();
		p.setId(1);
		p.setPrice("1500");
		p.setProductName("mouse");
		p.setProductCategory("accessories");
		Optional<Product> product = Optional.of(p);

		ProductDTO dto = new ProductDTO(1, "mouse", "1700", "accessories");

		when(productRepository.findById(1)).thenReturn(product);
	     
		when(productRepository.save(product.get())).thenReturn(p);
	
		boolean status = productService.updateProductDetails(dto);
		
		assertEquals(true, status);
	}
	
	@Test
	 void deleteProductById() {
		
		boolean status = productService.deleteProductById(1);
	    productRepository.deleteById(1);
	    assertEquals(true, status);
	}
}