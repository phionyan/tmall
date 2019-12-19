package com.phion.tmall.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.PropertyValue;
import com.phion.tmall.pojo.Review;
import com.phion.tmall.pojo.User;
import com.phion.tmall.service.CategoryService;
import com.phion.tmall.service.ProductImageService;
import com.phion.tmall.service.ProductService;
import com.phion.tmall.service.PropertyValueService;
import com.phion.tmall.service.ReviewService;
import com.phion.tmall.service.UserService;
import com.phion.tmall.util.Result;
/**
 * 由于前端业务交错
 * 这个控制来统一处理前端的rest请求
 * @author 15037
 *
 */
@RestController
public class ForeRestfulController {
	
	@Autowired CategoryService categoryService;
	
	@Autowired UserService userService;
	
	@Autowired ProductService productService;
	
	@Autowired ProductImageService productImageService;
	
	@Autowired PropertyValueService propertyValueService;
	
	@Autowired ReviewService reviewService;
	
	/**
	 * 获取某个用户的推荐搜索关键词
	 * @return
	 */
	@GetMapping("hotWords/{id}")
	public Object getHotWords(@PathVariable(name="id")int id) {
		List<String> hotWords = new ArrayList<String>();
		hotWords.add("电视机");
		hotWords.add("华为手机");
		hotWords.add("ipad");
		hotWords.add("dior香水");
		Map<String,Object> data = new HashMap<>();
		data.put("hotWords",hotWords);
		return Result.success(data);
	}
	
	/**
	 * 获取某个用户的推荐分类
	 * @return
	 */
	@GetMapping("/recommendCategories/{uid}")
	public Object getRecommendCategories(@PathVariable(name="uid")int id) {
		List<Category> recommendCategories = categoryService.listRecommend(id);
		Map<String,Object> data = new HashMap<>();
		data.put("recommendCategories",recommendCategories);
		return Result.success(data);
	}
	
	/**
	 * 注册用户
	 * @return
	 */
	@PostMapping("/user_regist")
	public Object userRegist(@RequestBody User user) {
		if(userService.isExist(user.getName())) {
			return Result.fail("用户名已注册");
		}
		
        user.setName(HtmlUtils.htmlEscape(user.getName()));
		userService.add(user);
		return Result.success();
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@PostMapping("/user_login")
	public Object userLogin(@RequestBody User user,HttpSession session) {
	    user.setName(HtmlUtils.htmlEscape(user.getName()));
	 
	    user =userService.get(user.getName(),user.getPassword());
	    if(null==user){
	        String message ="账号密码错误";
	        return Result.fail(message);
	    }
	    else{
	        session.setAttribute("user", user);
	        return Result.success();
	    }
	}
	
	/*@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:home";
	}*/
	
	/**
	 * 获取产品信息
	 * @return
	 */
	@GetMapping("/product/{pid}")
	public Object getProduct(@PathVariable(name="pid")int id) {
		Product product = productService.get(id);
		/*productImageService.fillBriefs(product);*/
		
		productImageService.setFirstProdutImage(product);
		product.setProductBriefImages(productImageService.listBriefProductImages(id));
		product.setProductDetailImages(productImageService.listDetailProductImages(id));
		
		List<PropertyValue> pvs = propertyValueService.list(product);
	    List<Review> reviews = reviewService.list(product);
	    productService.setSaleAndReviewNumber(product);//前端需求，单独写业务层一个方法，逻辑更清晰
		
		Map<String,Object> data = new HashMap<>();
		data.put("product",product);
		data.put("pvs", pvs);
		data.put("reviews", reviews);
	    
		return Result.success(data);
	}
	
}
