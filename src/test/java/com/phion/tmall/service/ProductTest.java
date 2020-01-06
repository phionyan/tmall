package com.phion.tmall.service;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.dao.ProductDAO;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.util.Result;
import com.phion.tmall.web.ForeRestfulController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductTest {

	@Autowired ProductService productService;
	@Autowired ProductDAO productDAO;
	@Autowired ForeRestfulController foreRestfulController;
	
	@Test
	public void list() {
		List<Product> products = productService.list(13, 0, 5).getContent();
		Iterator<Product> iterator = products.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	@Test
	public void testsetSaleAndReviewNumber() {
		Product p = productService.get(3);
		productService.setSaleAndReviewNumber(p);
		System.out.println(p);
		System.out.println("over");
	}
	
	@Test
	public void testsearch() {
		//String keyword = "%' or '%1%'='%1";
		String keyword = "产品";
		List<Product> ps = productService.search(keyword, 0, 5);
		for(Product p : ps) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testsearchCategory() {
		//String keyword = "%' or '%1%'='%1";
		String keyword = "产品";
		List<Product> ps = productService.searchCategory(13, 0, 5);
		for(Product p : ps) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testOrder() {
		//List<Product> products = null;
		List<Product> products =  (List<Product>) ((Result)foreRestfulController.search( "产",new String("priceUp"))).getData();
		for(Product product : products) {
			System.err.println("getPromotePrice:   "+product.getPromotePrice());
		}
		System.out.println();
		products =  (List<Product>) ((Result)foreRestfulController.search("产",new String("priceDown"))).getData();
		for(Product product : products) {
			System.err.println("getPromotePrice:   "+product.getPromotePrice());
		}
		/*System.out.println();
		products =  productService.searchByReviewCountDown("",0);
		for(Product product : products) {
			System.out.println(product.getReviewCount());
		}*/
		/*System.out.println();
		products =  productService.searchBySaleCountDown("%%",12);
		for(Product product : products) {
			System.out.println(product.getSaleCount());
		}*/
		/*
		System.out.println();
		products =  productService.searchNewProducts("");
		for(Product product : products) {
			System.out.println(product.getCreateDate());
		}*/
		/*System.out.println();
		products =  productService.searchAllProducts("%奶%",0);
		for(Product product : products) {
			System.out.println(product.getName());
		}*/
		
		/*Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(0, 20, sort);
		List<Product> ps = null;
		
		ps = productDAO.findByNameLike("%%", pageable);*/
		
	}
}
