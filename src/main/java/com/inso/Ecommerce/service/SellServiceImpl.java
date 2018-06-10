package com.inso.Ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inso.Ecommerce.model.Sell;
import com.inso.Ecommerce.model.Sell.State;
import com.inso.Ecommerce.repository.SellRepository;

@Service
public class SellServiceImpl implements SellService{
	
	@Autowired
	private SellRepository repository;
	
	@Override
	public Sell save(Sell sell) {
		return sell == null ? null : repository.save(sell);
	}

	@Override
	public List<Sell> findAll() {
		return repository.findAll();
	}

	@Override
	public Sell findById(int id) {
		Optional<Sell> sell = repository.findById(id);
		return sell.isPresent() ? sell.get() : null;
	}
	
	@Override
	public List<Sell> findByState(int state){
		State st;
		switch(state) {
		case 0:
			st = State.RECEIVED;
			break;
		case 1:
			st = State.IN_PROGRESS;
			break;
		case 2:
			st = State.COMPLETED;
			break;
		default:
			st = null;
			break;
		}
		return repository.findByState(st);
	}

	@Override
	public void delete(Sell sell) {
		repository.delete(sell);
	}
	
	

}
