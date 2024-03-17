package com.memo.kart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.kart.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
