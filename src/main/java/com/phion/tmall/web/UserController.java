package com.phion.tmall.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phion.tmall.pojo.User;
import com.phion.tmall.service.UserService;
import com.phion.tmall.util.Page4Navigator;

@RestController
public class UserController {

	@Autowired UserService userService;
	
	@GetMapping(value = "/users")
	public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size){
		return userService.list(start,size);
	}

	@PostMapping(value = "/users")
	public Object add(User bean) throws IllegalStateException, IOException {
		userService.add(bean);
		return bean;
	}
	
	@DeleteMapping("/users/{id}")
	public Object delete(@PathVariable(value = "id")int id) {
		userService.delete(id);
		return null;
	}
	
	@PutMapping("/users/{id}")
	public Object update(User bean) throws IllegalStateException, IOException {
		userService.update(bean);
		return bean;
	}
	@GetMapping("/users/{id}")
	public Object get(@PathVariable(value = "id")int id) {
		return userService.get(id);
	}
}
