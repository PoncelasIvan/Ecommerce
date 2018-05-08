package com.inso.Ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inso.Ecommerce.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{}
