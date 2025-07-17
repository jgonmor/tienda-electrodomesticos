package com.jgonmor.sell_service.dto;


import com.jgonmor.sell_service.model.Sell;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SellDto {

    Long id;
    LocalDateTime date;
    CartDto cart;

    public Sell toModel(){
        Sell sell = new Sell();

        sell.setId(this.id);
        sell.setDate(this.date);
        sell.setCartId(this.cart.getId());

        return sell;
    }

}
