package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.ProductDAO;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.Review;
import com.phion.tmall.util.Page4Navigator;


@Service
public class ProductService {

	@Autowired ProductDAO productDAO;
	@Autowired CategoryService categoryService;
	@Autowired ReviewService reviewService;
	@Autowired OrderItemService orderItemService;
	
	public Page4Navigator<Product> list(int cid,int start,int size){
		Category category = categoryService.get(cid);
		
		Sort sort = new Sort(Sort.Direction.DESC,"id");
		Pageable pageable = new PageRequest(start, size,sort);//小心不要用awt的peageable
		Page pageFromJPA = productDAO.findByCategory(category, pageable);
		
		return new Page4Navigator<>(pageFromJPA);
	}
	
	public void add(Product product) {
		productDAO.save(product);
	}

	public void delete(int id) {
		productDAO.delete(id);
	}
	public void update(Product product) {
		productDAO.save(product);
	}
	public Product get(int id) {
		return productDAO.findOne(id);
	}
	

	
	/**
	 * 测试用
	 */
	public List<Product> list(){
		return productDAO.findAll();
	}

	/**
	 *前端需求，提供销量和评论数量
	 * @param product
	 */
	public void setSaleAndReviewNumber(Product product) {
		int reviewCount = reviewService.getCount(product);
		int saleCount = reviewService.getCount(product);
		product.setReviewCount(reviewCount);
		product.setSaleCount(saleCount);
	}
}
