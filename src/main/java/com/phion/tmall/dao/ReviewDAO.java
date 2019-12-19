package com.phion.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.Review;

public interface ReviewDAO extends JpaRepository<Review, Integer> {
	List<Review> findByProductOrderByIdDesc(Product product);

	int countByProduct(Product product);
}
