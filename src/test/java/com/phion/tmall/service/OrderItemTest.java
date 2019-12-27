package com.phion.tmall.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.pojo.OrderItem;
import com.phion.tmall.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderItemTest {

	@Autowired OrderItemService orderItemService;
	
	@Test
	public void testList() {
		User user = new User();
		user.setId(33);
		List<OrderItem> orderItems = orderItemService.listByUser(user);
		for(OrderItem oi : orderItems) {
			System.out.println(oi.getId()+"------"+oi.getProduct());
		}
	}
}
