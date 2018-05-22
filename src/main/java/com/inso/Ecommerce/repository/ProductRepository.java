package com.inso.Ecommerce.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inso.Ecommerce.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query("SELECT p FROM Product p WHERE p.title LIKE %:search% OR p.author LIKE %:search% ")
	List<Product> search(@Param("search") String search);
}
