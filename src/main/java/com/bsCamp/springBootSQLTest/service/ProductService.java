package com.bsCamp.springBootSQLTest.service;

import java.util.List;

import com.bsCamp.springBootSQLTest.enitity.Product;

public interface ProductService {

	public Product save(Product product);
	public List<Product> getProducts();
	public Product deleteProduct(int id);
	public Product getProduct(int id);
	public Product updateProduct(Product product);
	public List<Product> getProductWithQty(int qty);

}
