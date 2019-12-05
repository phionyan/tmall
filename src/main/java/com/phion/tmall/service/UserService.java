package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.UserDAO;
import com.phion.tmall.pojo.User;
import com.phion.tmall.util.Page4Navigator;

@Service
public class UserService {
	
	@Autowired UserDAO userDAO;
	
	public Page4Navigator<User> list(int start, int size) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		Page pageFromJPA =userDAO.findAll(pageable);
		return new Page4Navigator<User>(pageFromJPA);
	}
	
	public void add(User user) {
		userDAO.save(user);
	}
	
	public void delete(int id) {
		userDAO.delete(id);
	}
	
	public void update(User user) {
		userDAO.save(user);
	}
	
	public User get(int id) {
		return userDAO.findOne(id);
	}
	
	/**
	 * 用于测试
	 */
	public List<User> listAll() {
		return userDAO.findAll();
	}
}
