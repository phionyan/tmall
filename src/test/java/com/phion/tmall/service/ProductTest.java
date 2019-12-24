package com.phion.tmall.service;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductTest {

	@Autowired ProductService productService;
	
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
}
