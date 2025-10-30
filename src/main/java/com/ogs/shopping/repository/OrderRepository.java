package com.ogs.shopping.repository;

import com.ogs.shopping.entity.Order;
import com.ogs.shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserOrderByOrderDateDesc(User user);

}
