package com.phion.tmall.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.OrderItemDAO;
import com.phion.tmall.pojo.Order;
import com.phion.tmall.pojo.OrderItem;
import com.phion.tmall.pojo.User;

@Service
public class OrderItemService {
	
	@Autowired OrderItemDAO orderItemDAO;
	
	@Autowired ProductImageService productImageService;
	
	public List<OrderItem> list(){
		Sort sort = new Sort(Sort.Direction.ASC,"id");
		return orderItemDAO.findAll(sort);
	}
	
	public void add(OrderItem orderItem) {
		orderItemDAO.save(orderItem);
	}
	
	public void delete(int id) {
		orderItemDAO.delete(id);
	}
	
	public OrderItem get(int id) {
		OrderItem orderItem= orderItemDAO.findOne(id);
		return orderItem;
	}
	
	public void update(OrderItem orderItem) {
		orderItemDAO.save(orderItem);
	}

	/**
	 * 因为订单没有直接外键接订单项
	 * 没有直接查出来，所以需要我们自行填充数据
	 * @param orders
	 */
	public void fillItems(List<Order> orders) {
		for(Order order : orders) {
			//查出所有的订单项
			fill(order);
			System.out.println(order.getTotalNumber());
		}
	}

	private void fill(Order order) {
		List<OrderItem> orderItems = listByOrder(order);
		float totalPrice = 0;
		int totalNumber = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();//计算总金额
			totalNumber+=orderItem.getNumber();//计算总数量
			productImageService.setFirstProdutImage(orderItem.getProduct());//设置预览图片
			
			//由于OrderItem有Order字段，会引起Json无线递归，这里选择将其置空
			orderItem.setOrder(null);
		}
		order.setTotalPrice(totalPrice);
		order.setOrderItems(orderItems);
		order.setTotalNumber(totalNumber);		
		order.setOrderItems(orderItems);
	}

	private List<OrderItem> listByOrder(Order order) {
		return orderItemDAO.findByOrderOrderByIdDesc(order);
	}
	
	public Map<String,Object> listOrderItemsInfo(String[] oiids){
		Map<String,Object> orderItemsInfo = new HashMap<String, Object>();
		float total = 0;
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (String strid : oiids) {
	         int id = Integer.parseInt(strid);
	         OrderItem oi= get(id);
	         total +=oi.getProduct().getPromotePrice()*oi.getNumber();
	         orderItems.add(oi);
	     }
		
		orderItemsInfo.put("orderItems", orderItems);
		orderItemsInfo.put("total", total);
		return orderItemsInfo;
	}

	public List<OrderItem> listByUser(User user) {
		
		return orderItemDAO.findByUserOrderByIdDesc(user);
	}
	
}
