package com.jgonmor.sell_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplianceDTO {

    private Long id;
    private String name;
    private String description;
    private String brand;
    private int price;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApplianceDTO that = (ApplianceDTO) o;
        return price == that.price && Objects.equals(id,
                                                     that.id) && Objects.equals(name,
                                                                                that.name) && Objects.equals(description,
                                                                                                             that.description) && Objects.equals(brand,
                                                                                                                                                 that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                            name,
                            description,
                            brand,
                            price);
    }
}