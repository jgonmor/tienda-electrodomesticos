package com.jgonmor.sell_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    Long id;
    int total;
    List<ApplianceQuantityDTO> appliancesQuantity;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDTO = (CartDto) o;
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
