package com.bsCamp.springBootSQLTest.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bsCamp.springBootSQLTest.enitity.Product;
import com.bsCamp.springBootSQLTest.repository.ProductRepository;
import com.bsCamp.springBootSQLTest.service.ProductServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	@Mock //Fake Data for testing...
	ProductRepository productRepository;
    
	@InjectMocks
	ProductServiceImpl productServiceImpl;
	
	Product product = new Product();
	Product product2 = new Product();
	List<Product> products = new ArrayList<Product>();
	
	@BeforeEach
	private void setUp() {
		product.setP_id(1);
		product.setName("Coffee");
		product.setQuantity(500);
		product.setUnit_price(400);
		
		product2.setP_id(2);
		product2.setName("Ramen");
		product2.setQuantity(500);
		product2.setUnit_price(1000);
		
		products.add(product);
		products.add(product2);
	}
	
	@DisplayName("Test for save product in service layer")
	@Test
	void givenSaveProduct_SendProductDat_thenReturnProductObj() {
		given(productRepository.save(product)).willReturn(product);
		Product savedProduct = productServiceImpl.save(product);
		assertThat(savedProduct).isNotNull(); //return true or false
	}
	
	@DisplayName("Test for get all product in service layer")
	@Test
	void givenGetProducts_thenReturnProductWithList() {
		given(productRepository.findAll()).willReturn(products);
		List<Product> products = productServiceImpl.getProducts();
		assertThat(products).isNotNull(); //return true or false
		assertThat(products).contains(product);
		assertThat(products.size()).isEqualTo(2);

	}
	

	@DisplayName("Test for get product by id in service layer")
	@Test
	void givenGetProduct_sendId_thenReturnProductWithID() {
		given(productRepository.findById(1)).willReturn(Optional.of(product));
		Product productWithId = productServiceImpl.getProduct(1);
		assertThat(productWithId.getP_id()).isEqualTo(1);
		assertThat(productWithId.getName()).isEqualTo(product.getName());

	}
	

	@DisplayName("Test for delete product by id in service layer")
	@Test
	void givenDeleteProduct_sendId_thenReturnNull() {
		willDoNothing().given(productRepository).deleteById(1);
		productServiceImpl.deleteProduct(1);
		verify(productRepository, times(1)).deleteById(1);
	}
	
	@DisplayName("Test for update product by id in service layer")
	@Test
	void givenUpdateProduct_sendProduct_thenReturnUpdatedProduct() {
		given(productRepository.findById(product.getP_id())).willReturn(Optional.of(product));
		given(productRepository.save(product)).willReturn(product2);
		Product updatedProduct = productServiceImpl.updateProduct(product);
		assertThat(updatedProduct.getName()).isEqualTo(product2.getName());
		assertThat(updatedProduct.getP_id()).isEqualTo(product2.getP_id());
		assertThat(updatedProduct.getQuantity()).isEqualTo(product2.getQuantity());
		assertThat(updatedProduct.getUnit_price()).isEqualTo(product2.getUnit_price());




	}
}
