package com.phion.tmall.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.pojo.Order;
import com.phion.tmall.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderTest {
	
	@Autowired OrderService orderService;
	
	@Test
	public void test() {
		User user = new User();
		user.setId(33);
		List<Order> orders = orderService.listByUserWithoutDelete(user);
		System.out.println(orders.size());
		for(Order o : orders) {
			System.out.println(o.getId()+"\t"+o.getOrderCode()+"\t"+o.getAddress()+"\t"+o.getReceiver());
		}
	}
}
