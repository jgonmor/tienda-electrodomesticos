package com.jgonmor.sell_service.repository;

import com.jgonmor.sell_service.dto.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service" , url = "${CART_SERVICE_URL}")
public interface ICartApi {

    @GetMapping("/{id}")
    public CartDto getCartById(@PathVariable Long id);


}
