package com.jgonmor.cart_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Schema(description = "Cart model")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Cart {

    @Schema(description = "Unique Id of the cart", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Total amount", example = "100")
    private int total;

    @Schema(description = "List of appliances in the cart")
    @OneToMany(mappedBy = "cart")
    private List<ApplianceCart> applianceCarts;


}
