package com.jgonmor.sell_service.service.cart;

import com.jgonmor.sell_service.dto.CartDto;

public interface ICartService {

    public CartDto getCartById(Long id);

}
