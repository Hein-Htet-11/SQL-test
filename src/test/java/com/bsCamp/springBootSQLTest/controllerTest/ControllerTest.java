package com.bsCamp.springBootSQLTest.controllerTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bsCamp.springBootSQLTest.enitity.Product;
import com.bsCamp.springBootSQLTest.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class ControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean	
	ProductService productService;
	
	List<Product> products =  new ArrayList<Product>();
	Product p1 = new Product();
	Product p2 = new Product();

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
		p1.setName("Udon");
		p1.setP_id(1);
		p1.setQuantity(100);
		p1.setUnit_price(6000);
		
		p1.setName("Okonomiyaki");
		p1.setP_id(2);
		p1.setQuantity(50);
		p1.setUnit_price(4500);
		
		products.add(p1);
		products.add(p2);
	}
	
	@DisplayName("Test for fetching all products")
	@Test
	public void givenToGetAllProducts_thenReturnAllProducts() throws Exception {
		given(productService.getProducts()).willReturn(products);
		ResultActions response = mockMvc.perform(get("/product/get/products"));
		response
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(products.size())))
		.andExpect(jsonPath("$[0].p_id", is(p1.getP_id())))
		.andExpect(jsonPath("$[1].p_id", is(p2.getP_id())));//$ represents JSON file
	}

	@DisplayName("Test for product byID in controller")
	@Test
	public void givenToGetProduct_SendID_thenReturnProduct() throws Exception {
		given(productService.getProduct(p1.getP_id())).willReturn(p1);
		ResultActions response = mockMvc.perform(get("/product/get/product/{id}", p1.getP_id()));
		response
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.p_id", is(p1.getP_id())))
		.andExpect(jsonPath("$.name", is(p1.getName())));//$ represents JSON file
	}
	
	@DisplayName("Test for saving product in controller")
	@Test
	public void givenToSaveProduct_SendProductObj_thenReturnProduct() throws Exception {
		given(productService.save(
				any(Product.class)))
		        .willAnswer((invocation)->invocation.getArgument(0));
		ResultActions response = mockMvc.perform(
				post("/product/save")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(p1)));
		response
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.p_id", is(p1.getP_id())))
		.andExpect(jsonPath("$.name", is(p1.getName())));
	}


}
