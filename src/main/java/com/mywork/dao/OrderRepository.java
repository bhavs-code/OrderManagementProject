package com.mywork.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mywork.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
