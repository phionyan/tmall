package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.UserDAO;
import com.phion.tmall.pojo.User;
import com.phion.tmall.util.Page4Navigator;

@Service
@CacheConfig(cacheNames="users")
public class UserService {
	
	@Autowired UserDAO userDAO;

	@Cacheable(key="'users-page-'+#start+ '-' + #size")
	public Page4Navigator<User> list(int start, int size) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size,sort);
		Page pageFromJPA =userDAO.findAll(pageable);
		return new Page4Navigator<User>(pageFromJPA);
	}

	@CacheEvict(allEntries=true)
	public void add(User user) {
		userDAO.save(user);
	}

	@CacheEvict(allEntries=true)
	public void delete(int id) {
		userDAO.delete(id);
	}

	@CacheEvict(allEntries=true)
	public void update(User user) {
		userDAO.save(user);
	}

	@Cacheable(key="'users-'+#id")
	public User get(int id) {
		return userDAO.findOne(id);
	}
	
	/**
	 * 用于测试
	 */
	@Cacheable(key="'users-listAll'")
	public List<User> listAll() {
		return userDAO.findAll();
	}
	
	/**
	 * 检测用户名是否已存在
	 */
	public boolean isExist(String name) {
        User user = getByName(name);
        return null!=user;
    }

	/**
	 * 根据用户名查询用户
	 * @param name
	 * @return
	 */
	@Cacheable(key="'users-getByName-'+#name")
	public User getByName(String name) {
		User user = userDAO.findByName(name);
		return user;
	}

	/**
	 * 根据用户名与密码校验用户
	 * @param name
	 * @param password
	 * @return
	 */
	@Cacheable(key="'users-get-'+#name+'-'+#password")
	public User get(String name, String password) {
		return userDAO.getByNameAndPassword(name,password);
	}
}
