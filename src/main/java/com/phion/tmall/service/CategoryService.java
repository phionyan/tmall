package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.CategoryDAO;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.util.Page4Navigator;

@Service
public class CategoryService {

	@Autowired CategoryDAO categoryDAO;
	
	public Page4Navigator<Category> list(int start, int size) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		Page pageFromJPA =categoryDAO.findAll(pageable);
		return new Page4Navigator<Category>(pageFromJPA);
	}
	
	public List<Category> list(){
		Sort sort = new Sort(Sort.Direction.ASC,"id");
		return categoryDAO.findAll(sort);
	}
	
	public void add(Category bean) {
		categoryDAO.save(bean);
	}
	
	public void delete(int id) {
		categoryDAO.delete(id);
	}
	
	public Category get(int id) {
		Category category= categoryDAO.findOne(id);
		return category;
	}
	
	public void update(Category category) {
		categoryDAO.save(category);
	}
	
	
}
