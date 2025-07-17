package com.jgonmor.cart_service.repository;

import com.jgonmor.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, Long> {

}
