package com.jgonmor.cart_service.controller;

import com.jgonmor.cart_service.dto.ApplianceQuantityDTO;
import com.jgonmor.cart_service.dto.CartDTO;
import com.jgonmor.cart_service.service.appliance_in_cart.IApplianceInCartService;
import com.jgonmor.cart_service.service.cart.ICartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private ICartService cartService;
    private IApplianceInCartService applianceInCartService;

    public CartController(ICartService cartService,
                          IApplianceInCartService applianceInCartService) {
        this.cartService = cartService;
        this.applianceInCartService = applianceInCartService;
    }

    @GetMapping("/{id}")
    public CartDTO getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @GetMapping("/{id}/appliances")
    public List<ApplianceQuantityDTO> getAppliancesInCart(@PathVariable Long id) {
        return applianceInCartService.getAppliancesInCart(id);
    }

    @PostMapping
    public CartDTO saveCart(@RequestBody CartDTO cart) {
        return cartService.saveCart(cart);
    }


    @PutMapping("/{id}/add")
    public CartDTO updateCart(@PathVariable Long id, @RequestBody ApplianceQuantityDTO product) {
        return cartService.addProduct(id, product);
    }

    @DeleteMapping("/{id}/product/{productId}")
    public CartDTO removeProduct(@PathVariable Long id, @PathVariable Long productId) {
        System.out.println("Producto a eliminar: " + productId);
        return cartService.removeProduct(id, productId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCart(@PathVariable Long id) {
        return cartService.deleteCart(id);
    }

}
