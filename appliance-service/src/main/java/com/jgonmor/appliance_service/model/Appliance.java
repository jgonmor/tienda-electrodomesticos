package com.jgonmor.appliance_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Schema(description = "Appliance model")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Appliance {

    @Schema(description = "Unique Id of the appliance", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name of the appliance", example = "Fridge")
    private String name;

    @Schema(description = "Description of the appliance", example = "Fridge description")
    private String description;

    @Schema(description = "Brand of the appliance", example = "Fridge brand")
    private String brand;

    @Schema(description = "Price of the appliance", example = "100")
    private int price;
}
