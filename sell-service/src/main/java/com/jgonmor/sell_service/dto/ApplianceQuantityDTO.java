package com.jgonmor.sell_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceQuantityDTO {
    private Long id;
    private ApplianceDTO appliance;
    private int quantity;
    private int total;


    public ApplianceQuantityDTO(Long id, ApplianceDTO appliance, int quantity) {
        this.id = id;
        this.appliance = appliance;
        this.quantity = quantity;
        this.total = appliance.getPrice() * quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApplianceQuantityDTO that = (ApplianceQuantityDTO) o;
        return quantity == that.quantity && total == that.total && Objects.equals(id,
                                                                                  that.id) && Objects.equals(appliance,
                                                                                                             that.appliance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                            appliance,
                            quantity,
                            total);
    }
}