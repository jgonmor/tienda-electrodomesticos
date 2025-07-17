package com.jgonmor.appliance_service.controller;

import com.jgonmor.appliance_service.dto.ApplianceRequestDTO;
import com.jgonmor.appliance_service.model.Appliance;
import com.jgonmor.appliance_service.service.IApplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appliances")
@Tag(name = "Appliance Service", description = "Gestión de productos")
public class ApplianceController {

    @Autowired
    private IApplianceService applianceService;

    @Operation(summary = "Get all appliances", description = "Get all appliances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of appliances"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Appliance> getAllAppliances() {
        return applianceService.getAllAppliances();
    }

    @Operation(summary = "Get all appliances", description = "Get all appliances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of appliances"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/idList/{idList}")
    public List<Appliance> getAppliancesInListId(@PathVariable List<Long> idList) {
        return applianceService.getAllAppliancesInListId(idList);
    }

    @Operation(summary = "Get appliance by id", description = "Get appliance by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appliance"),
            @ApiResponse(responseCode = "404", description = "Appliance not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public Appliance getApplianceById(@PathVariable Long id) {
        return applianceService.getApplianceById(id);
    }

    @Operation(summary = "Delete appliance by id", description = "Delete appliance by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appliance deleted"),
            @ApiResponse(responseCode = "404", description = "Appliance not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public void deleteAppliance(@PathVariable Long id) {
        applianceService.deleteAppliance(id);
    }

    @Operation(summary = "Save appliance", description = "Save appliance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appliance saved"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public Appliance saveAppliance(@Valid @RequestBody ApplianceRequestDTO appliance) {

        Appliance applianceToSave = new Appliance();
        applianceToSave.setName(appliance.getName());
        applianceToSave.setDescription(appliance.getDescription());
        applianceToSave.setBrand(appliance.getBrand());
        applianceToSave.setPrice(appliance.getPrice());

        return applianceService.saveAppliance(applianceToSave);

    }

    @Operation(summary = "Update appliance by id", description = "Update appliance by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appliance updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public Appliance updateAppliance(@PathVariable Long id, @Valid @RequestBody ApplianceRequestDTO appliance) {

        Appliance applianceToUpdate = new Appliance();
        applianceToUpdate.setName(appliance.getName());
        applianceToUpdate.setDescription(appliance.getDescription());
        applianceToUpdate.setBrand(appliance.getBrand());
        applianceToUpdate.setPrice(appliance.getPrice());

        return applianceService.updateAppliance(id, applianceToUpdate);

    }
}
