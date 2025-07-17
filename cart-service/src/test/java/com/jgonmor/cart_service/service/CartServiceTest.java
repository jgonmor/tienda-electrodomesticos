package com.jgonmor.cart_service.service;

import com.jgonmor.cart_service.dto.ApplianceDTO;
import com.jgonmor.cart_service.dto.ApplianceQuantityDTO;
import com.jgonmor.cart_service.dto.CartDTO;
import com.jgonmor.cart_service.model.ApplianceCart;
import com.jgonmor.cart_service.model.Cart;
import com.jgonmor.cart_service.repository.IApplianceApi;
import com.jgonmor.cart_service.repository.ICartRepository;
import com.jgonmor.cart_service.service.appliance_in_cart.IApplianceInCartService;
import com.jgonmor.cart_service.service.cart.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CartServiceTest {

    @Mock
    private ICartRepository cartRepository;

    @Mock
    private IApplianceInCartService applianceInCartService;

    @InjectMocks
    private CartService cartService;

    public List<ApplianceCart> APPLIANCE_CART_PREPARED_LIST = List.of(ApplianceCart.builder().id(1L).applianceId(1L).quantity(1).build());

    public Cart CART_PREPARED = Cart.builder()
            .id(1L)
            .total(100)
            .applianceCarts(
                    APPLIANCE_CART_PREPARED_LIST
            )
            .build();


    public Cart CART_MODIFIED_PREPARED = Cart.builder().id(1L)
            .total(100)
            .applianceCarts(
                    APPLIANCE_CART_PREPARED_LIST
            )
            .build();

    public CartDTO CART_DTO_PREPARED = new CartDTO(1L, 100,
            List.of(
                    new ApplianceQuantityDTO(1L, new ApplianceDTO(1L, "name", "description", "brand", 100), 1)
            )
    );

    public ApplianceDTO APPLIANCE_DTO_PREPARED = new ApplianceDTO(1L, "name", "description", "brand", 100);

    public ApplianceQuantityDTO APPLIANCE_QUANTITY_DTO_PREPARED = new ApplianceQuantityDTO(1L, APPLIANCE_DTO_PREPARED, 1);

    public List<ApplianceQuantityDTO> APPLIANCE_QUANTITY_DTO_PREPARED_LIST = List.of(APPLIANCE_QUANTITY_DTO_PREPARED);

    public List<ApplianceDTO> APPLIANCE_DTO_PREPARED_LIST = List.of(APPLIANCE_DTO_PREPARED);



    @Test
    void saveCartTest() {
        when(cartRepository.save(any(Cart.class))).thenReturn(CART_PREPARED);
        when(applianceInCartService.addApplianceToCart(any(Cart.class), any(ApplianceQuantityDTO.class))).thenReturn(APPLIANCE_QUANTITY_DTO_PREPARED);
        CartDTO cartDTO = cartService.saveCart(CART_DTO_PREPARED);
        assert cartDTO.getId() == 1L;
        assert cartDTO.getTotal() == 100;
    }

    @Test
    void getCartByIdTest() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(CART_PREPARED));
        when(applianceInCartService.getAppliancesInCart(any(Long.class))).thenReturn(APPLIANCE_QUANTITY_DTO_PREPARED_LIST);
        CartDTO cartDTO = cartService.getCartById(1L);
        assert cartDTO.getId() == 1L;
        assert cartDTO.getTotal() == 100;
        assert cartDTO.getAppliancesQuantity().contains(APPLIANCE_QUANTITY_DTO_PREPARED);
    }



    @Test
    void addProductTest() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(CART_PREPARED));
        when(applianceInCartService.addApplianceToCart(any(Cart.class), any(ApplianceQuantityDTO.class))).thenReturn(APPLIANCE_QUANTITY_DTO_PREPARED);
        when(applianceInCartService.getAppliancesInCart(any(Long.class))).thenReturn(APPLIANCE_QUANTITY_DTO_PREPARED_LIST);
        CartDTO cartDTO = cartService.addProduct(1L, APPLIANCE_QUANTITY_DTO_PREPARED);
        assert cartDTO.getId() == 1L;
        assert cartDTO.getTotal() == 100;
        assert cartDTO.getAppliancesQuantity().contains(APPLIANCE_QUANTITY_DTO_PREPARED);
    }

    @Test
    void removeProductTest() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(CART_PREPARED));

        CartDTO cartDTO = cartService.removeProduct(1L, 1L);
        assert cartDTO.getId() == 1L;
        assert cartDTO.getTotal() == 0;
    }
}
