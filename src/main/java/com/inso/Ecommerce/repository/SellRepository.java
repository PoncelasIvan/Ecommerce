package com.inso.Ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inso.Ecommerce.model.Sell;
import com.inso.Ecommerce.model.Sell.State;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer>{
	List<Sell> findByState(State state);
}
