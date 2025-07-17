package com.jgonmor.cart_service.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "appliance_cart",
        indexes = {@Index(name = "idx_cart_id",  columnList="cart_id")})
public class ApplianceCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cart cart;

    private Long applianceId;

    private int quantity;

}
