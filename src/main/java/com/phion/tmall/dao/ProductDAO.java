package com.phion.tmall.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Product;

public interface ProductDAO extends JpaRepository<Product,Integer> {
	Page<Product> findByCategory(Category category,Pageable pageable);

	List<Product> findByNameLike(String string, Pageable pageable);
	
	List<Product> findByCategory(String string, Pageable pageable);
}
