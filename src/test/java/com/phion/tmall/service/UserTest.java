package com.phion.tmall.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserTest {

	@Autowired UserService userService;
	
	@Test
	public void test() {
		User user = userService.get(1);
		System.out.println(user.getAnonymousName());
	}
	
	/*
	@Test
	public void add() {
		for(int i = 0 ; i< 10 ; i++) {
			User user = new User();
			user.setName("user"+(i+10));
			user.setPassword("password"+(i+10));
			userService.add(user);
		}
		System.out.println("-------插入完成------------");
	}*/
}
