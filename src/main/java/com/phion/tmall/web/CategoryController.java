package com.phion.tmall.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phion.tmall.pojo.Category;
import com.phion.tmall.service.CategoryService;
import com.phion.tmall.util.Page4Navigator;

@RestController
public class CategoryController {
	
	@Autowired CategoryService categoryService;
	
	@GetMapping(value = "/categories")
	public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size){
		return categoryService.list(start,size);
	}

}
