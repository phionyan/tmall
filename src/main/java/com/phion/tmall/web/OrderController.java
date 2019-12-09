package com.phion.tmall.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phion.tmall.pojo.Order;
import com.phion.tmall.service.OrderItemService;
import com.phion.tmall.service.OrderService;
import com.phion.tmall.util.OrderType;
import com.phion.tmall.util.Page4Navigator;
import com.phion.tmall.util.Result;

@RestController
public class OrderController {

	@Autowired OrderService orderService;
	@Autowired OrderItemService orderItemSrvice;
	
	@GetMapping(value = "/orders")
	public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size){
		Page4Navigator<Order> page = orderService.list(start,size); 
		orderItemSrvice.fillItems(page.getContent());
		return page;
	}

	@PostMapping(value = "/orders")
	public Object add(Order bean) {
		orderService.add(bean);
		return bean;
	}
	
	@DeleteMapping("/orders/{id}")
	public Object delete(@PathVariable(value = "id")int id) {
		orderService.delete(id);
		return null;
	}
	
	@PutMapping("/orders/{id}")
	public Object update(Order bean) {
		orderService.update(bean);
		return bean;
	}
	@GetMapping("/orders/{id}")
	public Object get(@PathVariable(value = "id")int id) {
		return orderService.get(id);
	}
	
	@PutMapping("deliverOrder/{id}")
    public Object deliverOrder(@PathVariable int id) {
        Order order = orderService.get(id);
        order.setDeliveryDate(new Date());
        order.setStatus(OrderType.WAIT_CONFIRM.toString());
        orderService.update(order);
        return Result.success();
    }
	
}
