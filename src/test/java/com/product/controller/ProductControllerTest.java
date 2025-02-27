package com.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.dto.ProductDTO;
import com.product.service.ProductService;

@WebMvcTest(controllers = ProductController.class)
 class ProductControllerTest {

	@MockitoBean
	private ProductService productService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	 void saveProductToDB_FailureTest() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductName("Laptop");
		productDTO.setPrice("50000");

		Mockito.when(productService.saveProductToDB(Mockito.any(ProductDTO.class))).thenReturn(false);

		MvcResult result = mockMvc.perform(post("/product/save").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(productDTO))).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		System.out.println(status);

		assertEquals(500, status);
	}

	@Test
	 void saveProductToDB_SuuccessTest() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductName("Laptop");
		productDTO.setPrice("50000");

		Mockito.when(productService.saveProductToDB(Mockito.any(ProductDTO.class))).thenReturn(true);

		MvcResult result = mockMvc.perform(post("/product/save").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(productDTO))).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		System.out.println(status);

		assertEquals(201, status);
	}

	@Test
	 void getAllProductDataTestSuucess() throws Exception {

		List<ProductDTO> productDTOList = new ArrayList<>();
		productDTOList.add(new ProductDTO(1, "Mobile", "15000", "Electronics"));
		productDTOList.add(new ProductDTO(2, "Laptop", "35000", "Electronics"));

		when(productService.getAllProductFromDB()).thenReturn(productDTOList);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/getAllProduct");

		int status = mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();

		assertEquals(200, status);
	}

	@Test
	 void getAllProductDataTestFailure() throws Exception {

		List<ProductDTO> productDTOList = new ArrayList<>();

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/getAllProduct");

		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

		assertEquals(0, response.getContentLength());

	}

	@Test
	void getProductByNameTest() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/product/getProductByName/productName").param("productName", "Mobile");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(200, status);
	}

	@Test
	 void getProductByCategoryTest() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/product/getProductByCategory/categoryName").param("categoryName", "Electronics");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(200, status);
	}

	@Test
	void updateProductDetailsSuccessTest() throws Exception {

		ProductDTO productDTO = new ProductDTO(1, "Mobile", "17000", "Electronics");

		when(productService.updateProductDetails(new ProductDTO(1, "Mobile", "17000", "Electronics"))).thenReturn(true);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(productDTO));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		System.out.println(response);
		
		assertEquals(200, response.getStatus());
		
		assertEquals("Product Updated Successfully", response.getContentAsString());
	}
	@Test
	 void updateProductDetailsFailureTest() throws Exception {

		ProductDTO productDTO = new ProductDTO(1, "Mobile", "17000", "Electronics");

		when(productService.updateProductDetails(new ProductDTO(1, "Mobile", "17000", "Electronics"))).thenReturn(false);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(productDTO));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		System.out.println(response);
		
		assertEquals(500, response.getStatus());
		
		assertEquals("SOMETHING WENT WRONG !!!", response.getContentAsString());
	}
	
	@Test
	 void deleteProductByIdSuccessTest() throws Exception {
		
		when(productService.deleteProductById(2)).thenReturn(true);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/product/delete/2");
				
	
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
	
		String response = mvcResult.getResponse().getContentAsString();
		System.out.println(mvcResult.getResponse());
	
		assertEquals("Product Deleted Successfully", response);
		
	}
	
	@Test
	 void deleteProductByIdFailureTest() throws Exception {
		
		when(productService.deleteProductById(2)).thenReturn(false);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/product/delete/2");
				
	
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
	
		String response = mvcResult.getResponse().getContentAsString();
		System.out.println(mvcResult.getResponse());
	
		assertEquals("SOMETHING WENT WRONG !!!", response);
		
	}
}
