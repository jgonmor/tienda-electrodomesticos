package com.jgonmor.cart_service.service.cart;

import com.jgonmor.cart_service.dto.ApplianceQuantityDTO;
import com.jgonmor.cart_service.dto.CartDTO;

import com.jgonmor.cart_service.model.Cart;
import com.jgonmor.cart_service.repository.IApplianceApi;
import com.jgonmor.cart_service.repository.ICartRepository;
import com.jgonmor.cart_service.service.appliance_in_cart.IApplianceInCartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {

    private final ICartRepository cartRepository;
    private final IApplianceInCartService applianceInCartService;

    public CartService(ICartRepository cartRepository,
                       IApplianceInCartService applianceInCartService,
                       IApplianceApi applianceRepository) {

        this.cartRepository = cartRepository;
        this.applianceInCartService = applianceInCartService;
    }



    @Override
    public CartDTO saveCart(CartDTO cart) {

        Cart toSave = new Cart();
        Cart cartSaved = cartRepository.save(toSave);
        List<ApplianceQuantityDTO> appliancesQuantity = new ArrayList<>();

        cart.setId(cartSaved.getId());

        cart.getAppliancesQuantity().parallelStream().forEach(
                applianceQuantityDTO -> {
                    appliancesQuantity.add(applianceInCartService.addApplianceToCart(cartSaved, applianceQuantityDTO));
                }
        );
        cart.setAppliancesQuantity(appliancesQuantity);


        cartSaved.setTotal(cart.getAppliancesQuantity().stream().mapToInt(
                ApplianceQuantityDTO::getTotal).sum());

        cartRepository.save(cartSaved);

        cart.setTotal(cartSaved.getTotal());

        return cart;
    }


    @Override
    public CartDTO getCartById(Long id) {

        Cart cart = cartRepository.findById(id).orElse(null);
        CartDTO cartDTO = new CartDTO();

        cartDTO.setId(cart.getId());
        cartDTO.setTotal(cart.getTotal());

        List<ApplianceQuantityDTO> appliancesQuantity = new ArrayList<>();

        appliancesQuantity = applianceInCartService.getAppliancesInCart(id);

        cartDTO.setAppliancesQuantity(appliancesQuantity);

        return cartDTO;
    }

    @Override
    public boolean deleteCart(Long id) {

            cartRepository.deleteById(id);

            return !cartRepository.existsById(id);

    }
    @Override
    public CartDTO addProduct(Long cartId,
                              ApplianceQuantityDTO applianceQuantityDTO) {

        int total = 0;
        CartDTO cartDTO = new CartDTO();
        List<ApplianceQuantityDTO> appliancesQuantity = new ArrayList<>();
        Cart cart = cartRepository.findById(cartId).orElse(null);

        applianceInCartService.addApplianceToCart(cart, applianceQuantityDTO);

        appliancesQuantity = applianceInCartService.getAppliancesInCart(cartId);

        total = appliancesQuantity.stream().mapToInt(ApplianceQuantityDTO::getTotal).sum();

        cart.setTotal(total);
        cartRepository.save(cart);
        cartDTO.setId(cart.getId());
        cartDTO.setAppliancesQuantity(appliancesQuantity);
        cartDTO.setTotal(cart.getTotal());

        return cartDTO;
    }

    @Override
    public CartDTO removeProduct(Long cartId,
                                 Long productId) {

        int total = 0;
        CartDTO cartDTO = new CartDTO();
        List<ApplianceQuantityDTO> appliancesQuantity = new ArrayList<>();

        Cart cart = cartRepository.findById(cartId).orElse(null);
// TODO: optimizar si se queda vacio el cart
        applianceInCartService.removeApplianceFromCart(cartId, productId);
        appliancesQuantity = applianceInCartService.getAppliancesInCart(cartId);

        total = appliancesQuantity.stream().mapToInt( applianceQuantityDTO -> {
            System.out.println("Total: " + applianceQuantityDTO.getTotal());
           return applianceQuantityDTO.getTotal();
        }).sum();

        cart.setTotal(total);
        cartRepository.save(cart);
        cartDTO.setId(cart.getId());
        cartDTO.setAppliancesQuantity(appliancesQuantity);
        cartDTO.setTotal(cart.getTotal());

        return cartDTO;

    }


}
