package com.inso.Ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inso.Ecommerce.model.Product;
import com.inso.Ecommerce.model.ProductSell;
import com.inso.Ecommerce.model.Sell;

@Repository
public interface ProductSellRepository extends JpaRepository<ProductSell, Integer>{

	List<Product> findBySell(Sell sell);
	List<Sell> findByProduct(Product product);

}
