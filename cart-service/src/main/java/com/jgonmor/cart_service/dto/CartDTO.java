package com.jgonmor.cart_service.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    Long id;
    int total;
    List<ApplianceQuantityDTO> appliancesQuantity;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartDTO cartDTO = (CartDTO) o;
        return total == cartDTO.total && Objects.equals(id,
                                                        cartDTO.id) && Objects.equals(appliancesQuantity,
                                                                                      cartDTO.appliancesQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                            total,
                            appliancesQuantity);
    }
}
