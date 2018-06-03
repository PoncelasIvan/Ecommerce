package com.inso.Ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inso.Ecommerce.model.Sell;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer>{
	List<Sell> findByState(int state);
}
