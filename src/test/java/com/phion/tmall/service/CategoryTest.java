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
import com.phion.tmall.pojo.Order;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CategoryTest {

	@Autowired CategoryService categoryService;
	
	@Test
	public void list() {
		Category p = categoryService.get(1);
		System.out.println(p);
	}
	
	/*@Test
	public void list() {
		List<Category> categories = categoryService.listRecommend(1);
		Iterator<Category> iterator = categories.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}*/
}
