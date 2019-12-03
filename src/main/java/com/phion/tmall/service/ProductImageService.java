package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.ProductImageDAO;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.ProductImage;
import com.phion.tmall.util.ProductImageType;


@Service
public class ProductImageService {

	@Autowired ProductImageDAO productImageDAO;
	@Autowired ProductService productService;
	
	
	public List<ProductImage> listBriefProductImages(int pid) {
		Product product = productService.get(pid);
		return productImageDAO.findByProductAndTypeOrderByIdDesc(product,ProductImageType.BRIEF.toString());
	}
	
	public List<ProductImage> listDetailProductImages(int pid) {
		Product product = productService.get(pid);
		return productImageDAO.findByProductAndTypeOrderByIdDesc(product, ProductImageType.DETAIL.toString());
	}
	
	public void add(ProductImage productImage) {
		productImageDAO.save(productImage);
	}

	public void delete(int id) {
		productImageDAO.delete(id);
	}
	public void update(ProductImage productImage) {
		productImageDAO.save(productImage);
	}
	public ProductImage get(int id) {
		return productImageDAO.findOne(id);
	}
	
	
}
