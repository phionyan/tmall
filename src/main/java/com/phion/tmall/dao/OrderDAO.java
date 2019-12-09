package com.phion.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phion.tmall.pojo.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {

}
