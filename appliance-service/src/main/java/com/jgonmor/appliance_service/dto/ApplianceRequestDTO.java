package com.jgonmor.appliance_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Appliance request DTO")
@Data
public class ApplianceRequestDTO {

    @Schema(description = "Name of the appliance", example = "Fridge")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Description of the appliance", example = "Fridge description")
    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private String description;

    @Schema(description = "Brand of the appliance", example = "Fridge brand")
    @NotBlank(message = "Brand is required")
    private String brand;

    @Schema(description = "Price of the appliance", example = "100")
    @Positive(message = "Price must be positive")
    private int price;
}
