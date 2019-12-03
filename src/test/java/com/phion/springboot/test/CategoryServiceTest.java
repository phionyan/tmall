package com.phion.springboot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CategoryServiceTest {

	@Autowired CategoryService categoryService;
	
	@Test
	public void get() {
		Category category = categoryService.get(13);
		System.out.println(category);
	}
}






