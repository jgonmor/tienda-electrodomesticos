package com.jgonmor.cart_service.service.cart;


import com.jgonmor.cart_service.dto.ApplianceQuantityDTO;
import com.jgonmor.cart_service.dto.CartDTO;

public interface ICartService {

    CartDTO saveCart(CartDTO cart);

    CartDTO getCartById(Long id);

    boolean deleteCart(Long id);

    CartDTO addProduct(Long cartId, ApplianceQuantityDTO applianceQuantityDTO);

    CartDTO removeProduct(Long cartId, Long productId);

}
