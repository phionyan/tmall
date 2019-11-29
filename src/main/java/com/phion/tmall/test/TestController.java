package com.phion.tmall.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phion.tmall.pojo.Category;
import com.phion.tmall.service.CategoryService;
import com.phion.tmall.util.Page4Navigator;

@Controller
public class TestController {
	@Autowired CategoryService categoryService;
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public Page4Navigator<Category> list(){
		Page4Navigator<Category> page = categoryService.list(0,5);
		return page;
	}
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}
}
