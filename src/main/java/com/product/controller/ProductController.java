package com.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.ProductDTO;
import com.product.service.ProductService;
import com.product.util.AppConstant;

@RestController
@RequestMapping("/product")
public class ProductController {

	public ProductService productService;
	

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;

		
	}

	@PostMapping("/save")
	public ResponseEntity<String> saveProductToDB(@RequestBody ProductDTO productDTO) {

	
		boolean status = productService.saveProductToDB(productDTO);

		if (status) {

			return new ResponseEntity<>(AppConstant.PRODUCT_SAVED_SUCC, HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(AppConstant.INTERNAL_SERVER_ERR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllProduct")
	public ResponseEntity<List<ProductDTO>> getAllProductData(){

		
		List<ProductDTO> productList = productService.getAllProductFromDB();
	
		return new ResponseEntity<>(productList, HttpStatus.OK);
		
	}
	
	@GetMapping("/getProductByName/{productName}")
	public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String productName){
		
		List<ProductDTO> productDTO = productService.getProductByName(productName);
	
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}
	
	
	@GetMapping("/getProductByCategory/{category}")
	public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable String category){
		
		 List<ProductDTO> productListByCategory = productService.getProductByCategory(category);
	
		return new ResponseEntity<>(productListByCategory, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateProductDetails(@RequestBody ProductDTO productDTO){
		
		boolean status = productService.updateProductDetails(productDTO);
	
		if (status) {

			return new ResponseEntity<>(AppConstant.PRODUCT_UPDATE_SUCC, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(AppConstant.INTERNAL_SERVER_ERR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<String> deleteProductDetails(@PathVariable Integer productId){
		
		boolean status = productService.deleteProductById(productId);
	
		if (status) {

			return new ResponseEntity<>(AppConstant.PRODUCT_DELETE_SUCC, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(AppConstant.INTERNAL_SERVER_ERR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
} 
