package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.ReviewDAO;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.Review;
 
@Service
public class ReviewService {
 
    @Autowired ReviewDAO reviewDAO;
    @Autowired ProductService productService;
 
    public void add(Review review) {
        reviewDAO.save(review);
    }
 
    public List<Review> list(Product product){
        List<Review> result =  reviewDAO.findByProductOrderByIdDesc(product);
        return result;
    }
 
    public int getCount(Product product) {
        return reviewDAO.countByProduct(product);
    }
     
}