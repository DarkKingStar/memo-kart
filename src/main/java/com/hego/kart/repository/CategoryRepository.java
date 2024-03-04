package com.hego.kart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hego.kart.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
