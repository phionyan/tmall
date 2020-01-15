package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.ReviewDAO;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.Review;
 
@Service
@CacheConfig(cacheNames="reviews")
public class ReviewService {
 
    @Autowired ReviewDAO reviewDAO;
    @Autowired ProductService productService;

	@CacheEvict(key="'reviews-list-'+ #review.product.id")
    public void add(Review review) {
        reviewDAO.save(review);
    }

	@Cacheable(key="'reviews-list-'+ #product.id")
    public List<Review> list(Product product){
        List<Review> result =  reviewDAO.findByProductOrderByIdDesc(product);
        return result;
    }

	@Cacheable(key="'reviews-getCount-'+ #product.id")
    public int getCount(Product product) {
        return reviewDAO.countByProduct(product);
    }
     
}