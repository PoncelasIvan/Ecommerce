package com.inso.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inso.Ecommerce.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{}
