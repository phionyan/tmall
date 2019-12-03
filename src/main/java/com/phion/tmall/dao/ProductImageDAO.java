package com.phion.tmall.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.ProductImage;

public interface ProductImageDAO extends JpaRepository<ProductImage, Integer> {
	List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
