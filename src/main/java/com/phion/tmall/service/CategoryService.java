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

import com.phion.tmall.dao.CategoryDAO;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.util.Page4Navigator;

@Service
@CacheConfig(cacheNames="categories")
public class CategoryService {

	@Autowired CategoryDAO categoryDAO;
	
	@Autowired ProductService productService;

	@Autowired ProductImageService productImageService;
	
	@Cacheable(key="'categories-page-'+#start+ '-' + #size")
	public Page4Navigator<Category> list(int start, int size) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		Page pageFromJPA =categoryDAO.findAll(pageable);
		return new Page4Navigator<Category>(pageFromJPA);
	}

	@Cacheable(key="'categories-all'")
	public List<Category> list(){
		Sort sort = new Sort(Sort.Direction.ASC,"id");
		return categoryDAO.findAll(sort);
	}
	
	@CacheEvict(allEntries=true)
	public void add(Category bean) {
		categoryDAO.save(bean);
	}
	
	@CacheEvict(allEntries=true)
	public void delete(int id) {
		categoryDAO.delete(id);
	}
	
	@Cacheable(key="'categories-one-'+ #id")
	public Category get(int id) {
		Category category= categoryDAO.findOne(id);
		return category;
	}
	/**
	 * 由于前台使用分页查询，此处更新一项应该同时更新所有缓存
	 * @param category
	 */
	@CacheEvict(allEntries=true)
	public void update(Category category) {
		categoryDAO.save(category);
	}
	
	@Cacheable(key="'categories-recommend- '+#uid")
	public List<Category> listRecommend(int uid) {
		//查出10条分类数据
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(0, 10,sort);
		Page pageFromJPA =categoryDAO.findAll(pageable);
		List<Category> categories = pageFromJPA.getContent();
		//填充推荐产品
		for(Category category : categories) {
			fill(category);
		}
		return categories;
	}

	private void fill(Category c) {
		//查出5个用于显示的产品集合（实际中要根据算法推荐）
		List<Product> products = productService.list(c.getId(), 0, 5).getContent();
		for(Product product : products) {
			//将每个产品的分类置空，避免序列化时死循环
			product.setCategory(null);
			//填充预览图片
			productImageService.setFirstProdutImage(product);
		}
		c.setProducts(products);
	}
}
