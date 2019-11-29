package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.CategoryDAO;
import com.phion.tmall.pojo.Category;

@Service
public class CategoryService {

	@Autowired CategoryDAO categoryDAO;
	
	public List<Category> list(){
		Sort sort = new Sort(Sort.Direction.ASC,"id");
		return categoryDAO.findAll(sort);
	}
	
	public void add(Category bean) {
		categoryDAO.save(bean);
	}
}
