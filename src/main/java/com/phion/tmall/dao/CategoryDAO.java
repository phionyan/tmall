package com.phion.tmall.dao;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.phion.tmall.pojo.Category;

public interface CategoryDAO extends JpaRepository<Category,Integer>{
	
}
