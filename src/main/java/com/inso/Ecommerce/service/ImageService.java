package com.inso.Ecommerce.service;

import com.inso.Ecommerce.model.Image;

public interface ImageService {
	
	Image save(Image image);
	
	void deleteById(Integer id);
}
