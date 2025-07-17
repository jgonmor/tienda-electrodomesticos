package com.jgonmor.appliance_service.dto;

import lombok.Data;

@Data
public class ApplianceResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String brand;

    private int price;
}
