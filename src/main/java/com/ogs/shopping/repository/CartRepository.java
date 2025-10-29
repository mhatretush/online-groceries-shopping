package com.ogs.shopping.repository;

import com.ogs.shopping.entity.Cart;
import com.ogs.shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUser(User user);
}
