package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.PropertyValueDAO;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.Property;
import com.phion.tmall.pojo.PropertyValue;

@Service
@CacheConfig(cacheNames="propertyValues")
public class PropertyValueService {

	@Autowired PropertyValueDAO propertyValueDAO;
	@Autowired ProductService productService;
	@Autowired PropertyService propertyService;

	@Caching(evict= {@CacheEvict(key="'propertyValues-one-'+ #bean.id"),
			@CacheEvict(key="'propertyValues-list-'+ #bean.prodcut.id")})
	public void add(PropertyValue bean) {
        propertyValueDAO.save(bean);
    }
	
	@CacheEvict(allEntries = true)
	public void delete(int id) {
        propertyValueDAO.delete(id);
    }


	@Caching(evict= {@CacheEvict(key="'propertyValues-one-'+ #bean.id"),
	@CacheEvict(key="'propertyValues-list-'+ #bean.prodcut.id")})
	public void update(PropertyValue bean) {
        propertyValueDAO.save(bean);
    }

	@Cacheable(key="'propertyValues-one-'+ #id")
	public PropertyValue get(int id) {
       return propertyValueDAO.findOne(id);
    }
	

	@Cacheable(key="'propertyValues-list-'+ #pid")
	public List<PropertyValue> list(int pid){
		Product product = productService.get(pid);
		return propertyValueDAO.findByProductOrderByIdDesc(product);
	}
	
	/**
	 * 初始化方法，当创建一个product的时候，就要声明其对应分类的属性
	 * @param product
	 */
	public void init(int pid) {
		Product product = productService.get(pid);
		List<Property> propertys= propertyService.listByCategory(product.getCategory());
		for (Property property: propertys) {
			PropertyValue propertyValue = getByPropertyAndProduct(product, property);
			if(null==propertyValue){
				propertyValue = new PropertyValue();
				propertyValue.setProduct(product);
				propertyValue.setProperty(property);
				propertyValueDAO.save(propertyValue);//如果是第一次执行，相当于add，否则相当于update
			}
		}
	}

	@Cacheable(key="'propertyValues-getByPropertyAndProduct-'+ #product.id+' - '+#property.id")
	private PropertyValue getByPropertyAndProduct(Product product, Property property) {
		return propertyValueDAO.getByPropertyAndProduct(property, product);
	}

	/**
	 * 用于测试
	 * @return
	 */

	@Cacheable(key="'propertyValues-listAll'")
	public List<PropertyValue> listAll(){
		return propertyValueDAO.findAll();
	}

	@Cacheable(key="'propertyValues-list-'+#product.id")
	public List<PropertyValue> list(Product product) {
		return propertyValueDAO.findByProductOrderByIdDesc(product);
	} 
}
