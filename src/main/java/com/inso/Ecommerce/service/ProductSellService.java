package com.inso.Ecommerce.service;

import java.util.List;

import com.inso.Ecommerce.model.Product;
import com.inso.Ecommerce.model.ProductSell;
import com.inso.Ecommerce.model.Sell;

public interface ProductSellService {

	List<Product> findBySell(Sell sell);
	
	List<Sell> findByProduct(Product product);
	
	void save(ProductSell productSell);
	
	void delete(ProductSell productSell);
	
}
