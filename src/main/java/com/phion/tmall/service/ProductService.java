package com.phion.tmall.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.phion.tmall.comparator.ProductAllComparator;
import com.phion.tmall.dao.ProductDAO;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.Review;
import com.phion.tmall.util.Page4Navigator;


@Service
public class ProductService {

	@Autowired ProductDAO productDAO;
	@Autowired CategoryService categoryService;
	@Autowired ReviewService reviewService;
	@Autowired OrderItemService orderItemService;
	
	public Page4Navigator<Product> list(int cid,int start,int size){
		Category category = categoryService.get(cid);
		
		Sort sort = new Sort(Sort.Direction.DESC,"id");
		Pageable pageable = new PageRequest(start, size,sort);//小心不要用awt的peageable
		Page pageFromJPA = productDAO.findByCategory(category, pageable);
		
		return new Page4Navigator<>(pageFromJPA);
	}
	
	public void add(Product product) {
		productDAO.save(product);
	}

	public void delete(int id) {
		productDAO.delete(id);
	}
	public void update(Product product) {
		productDAO.save(product);
	}
	public Product get(int id) {
		return productDAO.findOne(id);
	}
	

	
	/**
	 * 测试用
	 */
	public List<Product> list(){
		return productDAO.findAll();
	}

	/**
	 *前端需求，提供销量和评论数量
	 * @param product
	 */
	public void setSaleAndReviewNumber(Product product) {
		int reviewCount = reviewService.getCount(product);
		int saleCount = reviewService.getCount(product);
		product.setReviewCount(reviewCount);
		product.setSaleCount(saleCount);
	}
	/**
	 * 搜索符合要求的前20个产品，暂时采用数据库模糊查询
	 * @param keyword
	 * @param i
	 * @param j
	 * @return
	 */
	public List<Product> search(String keyword, int start, int size) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        List<Product> products =productDAO.findByNameLike("%"+keyword+"%",pageable);
        return products;
	}
	
	/**
	 * 搜索分类下的前20个产品
	 * @param cid
	 * @param start
	 * @param size
	 * @return
	 */
	public List<Product> searchCategory(int cid, int start, int size) {
		Category category = new Category();
		category.setId(cid);
		Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        List<Product> products =productDAO.findByCategory(category, pageable).getContent();
        return products;
	}
	/**
	 * 按价格模糊查询产品
	 * @return
	 */
	public List<Product> searchByPrice(String type,String keyword, int cid){
		Sort sort = null;
		/*System.err.println("type addr:"+System.identityHashCode(type));
		System.err.println("priceDown addr:"+System.identityHashCode("priceDown"));
		System.err.println("priceUp addr:"+System.identityHashCode("priceUp"));*/
		
		if(type.equals("priceDown"))
			sort = new Sort(Sort.Direction.DESC, "promotePrice");
		else {

			/*System.err.println("start-------------");
			System.err.println(type);
			System.err.println("type==priceUp:"+(type=="priceUp"));
			System.err.println("type==priceDown:"+(type=="priceDown"));
			System.err.println("--------");
			System.err.println("type==priceUp:"+type.equals("priceUp"));
			System.err.println("type==priceDown:"+type.equals("priceDown"));

			System.err.println("end-------------");*/
			sort = new Sort(Sort.Direction.ASC, "promotePrice");			
		}
		/*System.err.println("sort:"+sort.getOrderFor("promotePrice"));*/
		Pageable pageable = new PageRequest(0, 20, sort);
		if(cid>0) {
			Category category = new Category();
			category.setId(cid);
			return productDAO.findByNameLikeAndCategory(keyword, category, pageable);
		}
		return 	productDAO.findByNameLike(keyword, pageable);
	}
	/**
	 * 按评价数降序查询产品
	 * 此处暂时根据评价数量多少来比较人气
	 *1、先查出所有的产品
	 *2、设置其评价数量
	 *3、根据评价数量降序排序
	 * @param cid 
	 * @return
	 */
	public List<Product> searchByReviewCountDown(String keyword, int cid){
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(0, 20, sort);
		List<Product> ps = null;
		if(cid>0) {
			Category category = new Category();
			category.setId(cid);
			ps = productDAO.findByNameLikeAndCategory(keyword, category, pageable);
		}else {
			ps = productDAO.findByNameLike(keyword, pageable);
		}
		setSaleAndReviewNumber(ps);
		Collections.sort(ps,new Comparator<Product>() {

			@Override
			public int compare(Product p1, Product p2) {
				return p1.getReviewCount()-p2.getReviewCount();
			}
			
		});
		return ps;
	}
	/**
	 * 按销量数降序查询产品
	 *1、先查出所有的产品
	 *2、设置其销量
	 *3、根据销量降序排序
	 * @return
	 */
	public List<Product> searchBySaleCountDown(String keyword,int cid){
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(0, 20, sort);
		List<Product> ps = null;
		if(cid>0) {
			Category category = new Category();
			category.setId(cid);
			ps = productDAO.findByNameLikeAndCategory(keyword, category, pageable);
		}else {
			ps = productDAO.findByNameLike(keyword, pageable);
		}
		setSaleAndReviewNumber(ps);
		Collections.sort(ps,new Comparator<Product>() {

			@Override
			public int compare(Product p1, Product p2) {
				return p1.getSaleCount()-p2.getSaleCount();
			}
			
		});
		return ps;
	}
	
	/**
	 * 按商品上架日期升序查询产品
	 * @return
	 */
	public List<Product> searchNewProducts(String keyword,int cid){
		Sort sort = new Sort(Sort.Direction.DESC, "createDate");
		Pageable pageable = new PageRequest(0, 20, sort);
		if(cid>0) {
			Category category = new Category();
			category.setId(cid);
			return productDAO.findByNameLikeAndCategory(keyword, category, pageable);
		}
		return 	productDAO.findByNameLike(keyword, pageable);
	}
	
	/**
	 * 按照综合属性查找排序商品
	 * @return
	 */
	public List<Product> searchAllProducts(String keyword,int cid){
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(0, 20, sort);
		List<Product> ps = null;
		if(cid>0) {
			Category category = new Category();
			category.setId(cid);
			ps = productDAO.findByNameLikeAndCategory(keyword, category, pageable);
		}else {
			ps = productDAO.findByNameLike(keyword, pageable);
		}
		setSaleAndReviewNumber(ps);
		Collections.sort(ps,new ProductAllComparator());
		return ps;
	}
	
	public void setSaleAndReviewNumber(List<Product> ps) {
		for(Product p : ps ) {
			setSaleAndReviewNumber(p);
		}
	}
}
