package com.phion.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phion.tmall.pojo.Order;
import com.phion.tmall.pojo.OrderItem;
import com.phion.tmall.pojo.User;

public interface OrderItemDAO  extends JpaRepository<OrderItem, Integer>{

	List<OrderItem> findByOrderOrderByIdDesc(Order order);

	List<OrderItem> findByUserOrderByIdDesc(User user);

	List<OrderItem> findByUserAndOrderIsNull(User user);
}
