package com.product.service;

import java.util.List;

import com.product.dto.ProductDTO;

public interface ProductService {

	public boolean saveProductToDB(ProductDTO productDTO);
	
	public List<ProductDTO> getAllProductFromDB();
	
	public List<ProductDTO> getProductByName(String productName);
	
	public List<ProductDTO> getProductByCategory(String categoryName);
	
	public boolean updateProductDetails(ProductDTO productDTO);
	
	public boolean deleteProductById(int productId);
	

}
