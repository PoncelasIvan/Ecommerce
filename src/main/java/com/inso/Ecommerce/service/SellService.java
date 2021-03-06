package com.inso.Ecommerce.service;


import java.util.List;

import com.inso.Ecommerce.model.Sell;


public interface SellService {
	
	Sell save(Sell sell);
	
	List<Sell> findAll();
	
	Sell findById(int id);
	
	List<Sell> findByState(int state);
	
	void delete(Sell sell);
	
}
