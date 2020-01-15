package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames="properties")
public class PropertyService {
	
	@Autowired PropertyDAO propertyDAO;
	
	@Autowired CategoryService categoryService;
	
	@Cacheable(key="'properties-page-'+#cid+ '-' + #start+ '-' + #size")
	public Page4Navigator<Property> list(int cid, int start, int size){
		Category category = categoryService.get(cid);
		//降序排序，便于调试
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		
		Page pageFromJPA = propertyDAO.findByCategory(category, pageable);
		return new Page4Navigator<>(pageFromJPA);
	}


	@CacheEvict(allEntries=true)
	public void add(Property property) {
		propertyDAO.save(property);
	}

	@CacheEvict(allEntries=true)
	public void delete(int id) {
		propertyDAO.delete(id);
	}

	@CacheEvict(allEntries=true)
	public void update(Property property) {
		propertyDAO.save(property);
	}

	@Cacheable(key="'properties-one-'+ #id")
	public Property get(int id) {
		return propertyDAO.findOne(id);
	}


	@Cacheable(key="'properties-listByCategory-' +#category.id")
	public List<Property> listByCategory(Category category) {
		return propertyDAO.findByCategory(category);
	}

}
