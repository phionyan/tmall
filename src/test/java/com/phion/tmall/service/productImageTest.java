package com.phion.tmall.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.dao.ProductImageDAO;
import com.phion.tmall.pojo.ProductImage;
import com.phion.tmall.pojo.PropertyValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class productImageTest {

	@Autowired ProductImageDAO productImageDAO;
	
	@Test
	public void list() {
		/*ProductImage p = productImageDAO.getOne(1);
		System.out.println(p);*/
	}
	/*@Test
	public void update() {
		int count = 0;
		List<ProductImage> images = productImageDAO.findAll();
		for(ProductImage p : images) {
			if(p.getType().equals("single")) {
				p.setType("brief");
			}
			productImageDAO.save(p);
			System.out.println(++count);
		}
		System.out.println("success");
	}*/
}
