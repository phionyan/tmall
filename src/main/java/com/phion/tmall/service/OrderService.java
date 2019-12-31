package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.phion.tmall.dao.OrderDAO;
import com.phion.tmall.pojo.Order;
import com.phion.tmall.pojo.OrderItem;
import com.phion.tmall.util.Page4Navigator;

@Service
public class OrderService {
	
	@Autowired OrderDAO orderDAO;
	@Autowired OrderItemService orderItemService;
	
	public Page4Navigator<Order> list(int start, int size) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		Page pageFromJPA =orderDAO.findAll(pageable);
		return new Page4Navigator<Order>(pageFromJPA);
	}
	
	//创建订单，业务方法
	@Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float createOrder(Order order, List<OrderItem> ois) {
        float total = 0;
        add(order);
 
        if(false)
            throw new RuntimeException();
 
        for (OrderItem oi: ois) {
            oi.setOrder(order);
			orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    } 
	
	public List<Order> list(){
		Sort sort = new Sort(Sort.Direction.ASC,"id");
		return orderDAO.findAll(sort);
	}
	
	public void add(Order bean) {
		orderDAO.save(bean);
	}
	
	public void delete(int id) {
		orderDAO.delete(id);
	}
	
	public Order get(int id) {
		Order order= orderDAO.findOne(id);
		return order;
	}
	
	public void update(Order order) {
		orderDAO.save(order);
	}
}
