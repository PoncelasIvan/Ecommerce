package com.inso.Ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inso.Ecommerce.model.Image;
import com.inso.Ecommerce.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService{

	@Autowired
	private ImageRepository repository;
	
	@Override
	public Image save(Image image) {
		return image == null ? null : repository.save(image);
	}

	@Override
	public void deleteById(Integer id) {
		if(id != null) repository.deleteById(id);
		return;
	}

}
