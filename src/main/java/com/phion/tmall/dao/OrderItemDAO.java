package com.phion.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phion.tmall.pojo.Order;
import com.phion.tmall.pojo.OrderItem;

public interface OrderItemDAO  extends JpaRepository<OrderItem, Integer>{

	List<OrderItem> findByOrderOrderByIdDesc(Order order);
}
