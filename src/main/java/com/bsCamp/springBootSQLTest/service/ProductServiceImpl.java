package com.bsCamp.springBootSQLTest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsCamp.springBootSQLTest.enitity.Product;
import com.bsCamp.springBootSQLTest.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	@Override
	public Product save(Product product) {
		// TODO Auto-generated method stub
		Product newProduct = productRepository.save(product);
		return newProduct;
	}
	@Override
	public List<Product> getProducts() {
		List<Product> products = productRepository.findAll();
		return products;
	}
	@Override
	public Product deleteProduct(int id) {
		productRepository.deleteById(id);
		return null;
		
	}
	@Override
	public Product getProduct(int id) {
		Product product = productRepository.findById(id).orElse(null);
		return product;
	}
	@Override
	public Product updateProduct(Product product) {
		Product original = productRepository.findById(product.getP_id()).orElse(null);
		if(original != null) {
			original.setName(product.getName());
			original.setQuantity(product.getQuantity());
			original.setUnit_price(product.getUnit_price());
			original = productRepository.save(original);
		}
		return original;
	}
	@Override
	public List<Product> getProductWithQty(int qty) {
		return productRepository.getProductQty(qty);
	}

}
