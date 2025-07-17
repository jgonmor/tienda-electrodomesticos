package com.jgonmor.sell_service.service.cart;

import com.jgonmor.sell_service.dto.CartDto;
import com.jgonmor.sell_service.repository.ICartApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService{

    @Autowired
    ICartApi cartApi;

    @Override
    public CartDto getCartById(Long id) {

        return cartApi.getCartById(id);
    }
}
