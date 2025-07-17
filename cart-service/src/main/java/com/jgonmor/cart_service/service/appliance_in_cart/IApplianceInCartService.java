package com.jgonmor.cart_service.service.appliance_in_cart;

import com.jgonmor.cart_service.dto.ApplianceDTO;
import com.jgonmor.cart_service.dto.ApplianceQuantityDTO;
import com.jgonmor.cart_service.model.ApplianceCart;
import com.jgonmor.cart_service.model.Cart;

import java.util.List;

public interface IApplianceInCartService {

    List<ApplianceQuantityDTO> getAppliancesInCart(Long cartId);

    ApplianceQuantityDTO addApplianceToCart(Cart cart, ApplianceQuantityDTO applianceQuantityDTO);

    ApplianceQuantityDTO addApplianceToCart(Cart cart, ApplianceCart applianceCart);

    void removeApplianceFromCart(Long cartId, Long applianceId);

    ApplianceQuantityDTO updateApplianceInCart(ApplianceQuantityDTO applianceQuantityDTO);

    ApplianceDTO getApplianceById(Long applianceId);
}
