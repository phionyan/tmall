package com.phion.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.Property;
import com.phion.tmall.pojo.PropertyValue;

public interface PropertyValueDAO extends JpaRepository<PropertyValue, Integer> {
	List<PropertyValue> findByProductOrderByIdDesc(Product product);
	PropertyValue getByPropertyAndProduct(Property property,Product product);
}
