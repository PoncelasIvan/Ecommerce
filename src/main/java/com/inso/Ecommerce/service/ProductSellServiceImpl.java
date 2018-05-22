package com.inso.Ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inso.Ecommerce.model.Product;
import com.inso.Ecommerce.model.ProductSell;
import com.inso.Ecommerce.model.Sell;
import com.inso.Ecommerce.repository.ProductSellRepository;

@Service
public class ProductSellServiceImpl implements ProductSellService{

	@Autowired
	private ProductSellRepository repository;

	@Override
	public List<Product> findBySell(Sell sell) {
		return sell == null ? null : repository.findBySell(sell);
	}

	@Override
	public List<Sell> findByProduct(Product product) {
		return product == null ? null : repository.findByProduct(product);
	}

	@Override
	public void save(ProductSell productSell) {
		if(productSell != null) repository.save(productSell);
	}

	@Override
	public void delete(ProductSell productSell) {
		if(productSell != null) repository.delete(productSell);		
	}
	
}
