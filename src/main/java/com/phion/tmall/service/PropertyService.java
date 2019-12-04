package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.PropertyDAO;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Property;
import com.phion.tmall.util.Page4Navigator;

@Service
public class PropertyService {
	
	@Autowired PropertyDAO propertyDAO;
	
	@Autowired CategoryService categoryService;
	
	public Page4Navigator<Property> list(int cid, int start, int size){
		Category category = categoryService.get(cid);
		//降序排序，便于调试
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		
		Page pageFromJPA = propertyDAO.findByCategory(category, pageable);
		return new Page4Navigator<>(pageFromJPA);
	}

	
	public void add(Property property) {
		propertyDAO.save(property);
	}
	
	public void delete(int id) {
		propertyDAO.delete(id);
	}
	
	public void update(Property property) {
		propertyDAO.save(property);
	}
	
	public Property get(int id) {
		return propertyDAO.findOne(id);
	}


	public List<Property> listByCategory(Category category) {
		return propertyDAO.findByCategory(category);
	}

}
