package com.phion.tmall.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Order;
import com.phion.tmall.pojo.OrderItem;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.PropertyValue;
import com.phion.tmall.pojo.Review;
import com.phion.tmall.pojo.User;
import com.phion.tmall.service.CategoryService;
import com.phion.tmall.service.OrderItemService;
import com.phion.tmall.service.OrderService;
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
	
	@Autowired OrderService orderService;
	
	@Autowired OrderItemService orderItemService;
	
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
	    System.out.println("userLogin");
		user.setName(HtmlUtils.htmlEscape(user.getName()));
	 
	    user =userService.get(user.getName(),user.getPassword());
	    //user = userService.get("phion","123456");
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
	
	/**
	 * 登录状态判断
	 * @param session
	 * @return
	 */
	@GetMapping("isLogin")
	public Object isLogin( HttpSession session) {
	    User user =(User)  session.getAttribute("user");
	   /* try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	    if(null!=user)
	        return Result.success();
	    return Result.fail("未登录");
	}
	
	/**
	 * searchPage.html 中的请求提交后，导致ForeRESTController.search()方法被调用
		1. 获取参数keyword
		2. 根据keyword进行模糊查询，获取满足条件的前20个产品
		3. 为这些产品设置销量和评价数量
		4. 返回这个产品集合
	 */
	@GetMapping("foresearch")
	public Object search( String keyword){
	    if(null==keyword)
	        keyword = "";
	    
	    List<Product> ps= productService.search(keyword,0,20);
	    productImageService.setFirstProdutImages(ps);
	    productService.setSaleAndReviewNumber(ps);
	    return ps;
	}
	
	@GetMapping("/foreCategory/{cid}")
	public Object searchCategory( @PathVariable(name="cid")int cid){
		List<Product> ps= productService.searchCategory(cid,0,20);
	    productImageService.setFirstProdutImages(ps);
	    productService.setSaleAndReviewNumber(ps);
	    return ps;
	}
	/**
	 * 如果有订单项id，则判断为已生成订单购买；如果没有，则执行加入购物车操作
	 * 
	 * @param num
	 * @param pid
	 * @param session
	 * @param oiid
	 * @return
	 */
	/*@GetMapping("/forebuyone")
	public Object buyOne(int num,int pid,HttpSession session,
			@RequestParam(name="oiid",defaultValue="0")int oiid) {
		if(oiid!=0) {
			//购买，生成订单
			
		}
		//加入购物车
		return buyoneAndAddCart(pid,num,session);
	}*/
	/**
	 * 立即购买（加入购物车)并跳转
	 * @param num
	 * @param pid
	 * @param session
	 * @return
	 */
	@GetMapping("/forebuyone")
	public Object buyOne(int num,int pid,HttpSession session) {
		return buyoneAndAddCart(pid,num,session);
	}
	
	/**
	 * 1、获取产品数量，如果足够方可购买
	 * 2、查询用户登录状态，获取当前用户
	 * 3、如果还有相同购物项，合并，否则加入购物车（生成orderItem）
	 * 4、返回订单项id供前端调用
	 * @param pid
	 * @param num
	 * @param session
	 * @return
	 */
	private Object buyoneAndAddCart(int pid, int num, HttpSession session) {
		//核查商品库存
		Product product = productService.get(pid);
		if(product.getStock()<num) return Result.fail("库存不足");
		//核查用户
		User user = (User) session.getAttribute("user");
		if(user==null) return Result.fail("用户未登录");
		int oiid = 0;
		//如果已有相同订单项，合并
		boolean found = false;
	    List<OrderItem> ois = orderItemService.listByUser(user);
	    for (OrderItem oi : ois) {
	        if(oi.getProduct().getId()==product.getId()){
	            oi.setNumber(oi.getNumber()+num);
	            orderItemService.update(oi);
	            found = true;
	            oiid = oi.getId();
	            break;
	        }
	    }
		if(!found) {
			//创建订单项（加入购物车）
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setUser(user);
			orderItem.setNumber(num);
			orderItemService.add(orderItem);
			oiid = orderItem.getId();
		}
		//返回订单项id
		Map<String,Object> data = new HashMap();
		data.put("oiid", oiid);
		return Result.success(data);
	}
	
	/**
	 * 生成订单
	 * 1、找到所有的订单项并计算总价
	 * 2、读取
	 * @param oiid
	 * @param session
	 * @return
	 */
	@GetMapping("forebuy")
	 public Object buy(String[] oiid,HttpSession session){
	     List<OrderItem> orderItems = new ArrayList<>();
	     float total = 0;//总价
	     
	     Map oisInfo = orderItemService.listOrderItemsInfo(oiid);
	     orderItems = (List<OrderItem>) oisInfo.get("orderItems");
	     total = (float) oisInfo.get("total");
	     
	     productImageService.setFirstProdutImagesOnOrderItems(orderItems);
	 
	     session.setAttribute("ois", orderItems);
	 
	     Map<String,Object> map = new HashMap<>();
	     map.put("orderItems", orderItems);
	     map.put("total", total);
	     return Result.success(map);
	 }

	@GetMapping("forecart")
	public Object cart(HttpSession session) {
	    User user =(User)  session.getAttribute("user");
	    System.out.println(user);
	    List<OrderItem> ois = orderItemService.listByUser(user);
	    productImageService.setFirstProdutImagesOnOrderItems(ois);
	    Map<String,Object> map = new HashMap<>();
	    map.put("orderItems", ois);
	    return Result.success(map);
	}
	
	@GetMapping("foredeleteOrderItem")
	public Object deleteOrderItem(HttpSession session,int oiid) {
		User user =(User)  session.getAttribute("user");
	    if(null==user)
	        return Result.fail("未登录");
	    orderItemService.delete(oiid);
	    return Result.success();
	}

}
