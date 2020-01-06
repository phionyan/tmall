package com.phion.tmall.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.phion.tmall.util.OrderType;
import com.phion.tmall.util.Result;
/**
 * 由于前端业务交错
 * 这个控制来统一处理前端的rest请求
 * @author 15037
 *
 */
@RestController
public class ForeRestfulController {
	
	private final static Logger logger  = LoggerFactory.getLogger(ForeRestfulController.class);
	
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
	public Object search( String keyword,String type){
		//System.err.println("--forsearch------");
		keyword="%"+keyword+"%";
		List<Product> ps = searchProducts(keyword,type,0);
		//System.err.println("product: "+ps.get(0));
		return Result.success(ps);
	}
	
	
	private List<Product> searchProducts(String keyword,String type,int cid) {
		logger.info("searchProducts----------keyword："+keyword+"\t"+"type:"+type+"\t"+"cid:"+cid);
		List<Product> ps = null;
		if(type!=null){
            switch(type){
                case "review":
                	ps = productService.searchByReviewCountDown(keyword,cid);
                    break;
                case "date" :
                	ps = productService.searchNewProducts(keyword,cid);
                    break;

                case "saleCount" :
                	ps = productService.searchBySaleCountDown(keyword,cid);
                    break;

                case "priceUp":
                	ps = productService.searchByPrice(type, keyword,cid);
                	//System.out.println("priceUp");
                    break;
                case "priceDown":
                	ps = productService.searchByPrice(type, keyword,cid);
                	//System.out.println("priceDown");
                    break;
                case "all":
                	ps = productService.searchAllProducts(keyword,cid);
                    break;
            }
        }
		productImageService.setFirstProdutImages(ps);
		return ps;
	}
	
	/**
	 * 根据分类查询产品
	 * @param cid
	 * @return
	 */
	@GetMapping("/foreCategory/{cid}")
	public Object searchCategory( @PathVariable(name="cid")int cid,String type){
		List<Product> ps= searchProducts("%%", type, cid);
	    return Result.success(ps);
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
/**
 * 查看购物车，加载用户的订单项
 * @param session
 * @return
 */
	@GetMapping("forecart")
	public Object cart(HttpSession session) {
	    User user =(User)  session.getAttribute("user");
	    //System.out.println(user);
	    List<OrderItem> ois = orderItemService.listByUser(user);
	    productImageService.setFirstProdutImagesOnOrderItems(ois);
	    Map<String,Object> map = new HashMap<>();
	    map.put("orderItems", ois);
	    return Result.success(map);
	}
	/**
	 * 删除订单项
	 * @param session
	 * @param oiid
	 * @return
	 */
	@GetMapping("foredeleteOrderItem")
	public Object deleteOrderItem(HttpSession session,int oiid) {
		User user =(User)  session.getAttribute("user");
	    if(null==user)
	        return Result.fail("未登录");
	    orderItemService.delete(oiid);
	    return Result.success();
	}
	
	/**
	 * 修改订单项
	 * @param session
	 * @param pid
	 * @param num
	 * @return
	 */
	@GetMapping("forechangeOrderItem")
	public Object changeOrderItem( HttpSession session, int pid, int num) {
	    User user =(User)  session.getAttribute("user");
	    if(null==user)
	        return Result.fail("未登录");
	 
	    List<OrderItem> ois = orderItemService.listByUser(user);
	    for (OrderItem oi : ois) {
	        if(oi.getProduct().getId()==pid){
	            oi.setNumber(num);
	            orderItemService.update(oi);
	            break;
	        }
	    }
	    return Result.success();
	}

	/**
	 * 创建订单
	 * 以时间+用户id生成唯一的订单编码
	 * 
	 * @param order
	 * @param session
	 * @return
	 */
	@PostMapping("forecreateOrder")
	public Object createOrder(@RequestBody Order order,HttpSession session) {
		User user =(User)  session.getAttribute("user");
	    if(null==user)
	        return Result.fail("未登录");
	    
	    //生成随机订单号= 时间+用户id
	    String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) 
	    		+user.getId();
	    
	    order.setOrderCode(orderCode);
	    order.setCreateDate(new Date());
	    order.setUser(user);
	    order.setStatus(OrderType.WAIT_PAY.toString());
	    List<OrderItem> ois= (List<OrderItem>)  session.getAttribute("ois");
	 
	    float total =orderService.createOrder(order,ois);
	 
	    Map<String,Object> map = new HashMap<>();
	    map.put("oid", order.getId());
	    map.put("total", total);
	 
	    return Result.success(map);
	}
	/**
	 * 支付逻辑，这里简单的更改订单状态
	 * @param oid
	 * @return
	 */
	@GetMapping("forepayed")
    public Object payed(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderType.WAIT_DELIVERY.toString());
        order.setPayDate(new Date());
        orderService.update(order);
        return Result.success(order);
    }
	
	/**
	 * 订单页数据
	 * 查出所有该用户未删除的订单（状态不为删除）并返回
	 * @param session
	 * @return
	 */
	@GetMapping("forebought")
	public Object bought(HttpSession session) {
	    User user =(User)  session.getAttribute("user");
	    if(null==user)
	        return Result.fail("未登录");
	    List<Order> os= orderService.listByUserWithoutDelete(user);
	    Map data = new HashMap<>();
	    data.put("orders", os);
	    return Result.success(data);
	}
	
	/**
	 * 确认付款
	 * 1、找到订单
	 * 2、将订单项信息填充到订单内
	 * 3、计算订单总价
	 * 4、返回订单信息
	 * @param oid
	 * @return
	 */
	@GetMapping("foreconfirmPay")
    public Object confirmPay(int oid) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        orderService.setTotalPrice(order);
        return Result.success(order);
    }
	/**
	 * 确认收货----》即同意支付宝将钱付给商家
	 * 找到订单，修改订单状态，修改支付日期
	 * @param oid
	 * @return
	 */
	@GetMapping("foreorderConfirmed")
	public Object orderConfirmed( int oid) {
	    Order order = orderService.get(oid);
	    if(order.getConfirmDate()==null) {
	    	order.setStatus(OrderType.WAIT_REVIEW.toString());
		    order.setConfirmDate(new Date());
		    orderService.update(order);
	    }
	    return Result.success();
	}
	
	/**
	 * 删除订单
	 * @param session
	 * @param oiid
	 * @return
	 */
	@GetMapping("foredeleteOrder")
	public Object deleteOrder(HttpSession session,int oid) {
		User user =(User)  session.getAttribute("user");
	    if(null==user)
	        return Result.fail("未登录");
	    Order order = orderService.get(oid);
	    order.setStatus(OrderType.DELETE.toString());
	    orderService.update(order);
	    return Result.success();
	}
	/**
	 * 评价页面，由于前期设计的时候没设计好，现在不好实现评价的逻辑，暂时对每个订单的第一个订单项进行评价
	 * 1、找到订单
	 * 2、找到第一个订单项
	 * 3、找到这个产品的所有评价
	 * 4、返回评价信息
	 * @param oid
	 * @return
	 */
	@GetMapping("forereview")
	public Object review(int oid) {
	    Order order = orderService.get(oid);
	    orderItemService.fill(order);
	    Product product = order.getOrderItems().get(0).getProduct();
	    
	    List<Review> reviews = reviewService.list(product);
	    productService.setSaleAndReviewNumber(product);
	    Map<String,Object> map = new HashMap<>();
	    map.put("product", product);
	    map.put("order", order);
	    map.put("reviews", reviews);
	 
	    return Result.success(map);
	}
	/**
	 * 提交评价
	 * @param session
	 * @param oid
	 * @param pid
	 * @param content
	 * @return
	 */
	@PostMapping("foredoreview")
	public Object doreview( HttpSession session,int oid,int pid,String content) {
	    //更新订单状态
		Order o = orderService.get(oid);
	    o.setStatus(OrderType.FINISH.toString());
	    orderService.update(o);
	    //为产品设置评价
	    Product p = productService.get(pid);
	    content = HtmlUtils.htmlEscape(content);
	    //为评价关联用户
	    User user =(User)  session.getAttribute("user");
	    Review review = new Review();
	    review.setContent(content);
	    review.setProduct(p);
	    review.setCreateDate(new Date());
	    review.setUser(user);
	    reviewService.add(review);
	    return Result.success();
	}
}
