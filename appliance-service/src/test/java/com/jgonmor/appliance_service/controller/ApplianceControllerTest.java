package com.jgonmor.appliance_service.controller;

import com.jgonmor.appliance_service.exceptions.ApplianceNotFoundException;
import com.jgonmor.appliance_service.model.Appliance;
import com.jgonmor.appliance_service.service.IApplianceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplianceController.class)
@Import({ValidationAutoConfiguration.class, GlobalExceptionHandle.class})
@ActiveProfiles("test")
public class ApplianceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IApplianceService applianceService;

    @Test
    void whenGetAllAppliances_thenReturnList() throws Exception {

        Appliance appliance1 = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 100);
        Appliance appliance2 = new Appliance(2L, "Refrigerator", "Refrigerator description", "Refrigerator brand", 200);
        List<Appliance> mockAppliances = List.of(appliance1, appliance2);

        when(applianceService.getAllAppliances()).thenReturn(mockAppliances);

        mockMvc.perform(MockMvcRequestBuilders.get("/appliances"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(2))
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].name").value("Fridge"))
               .andExpect(jsonPath("$[0].description").value("Fridge description"))
               .andExpect(jsonPath("$[0].brand").value("Fridge brand"))
               .andExpect(jsonPath("$[0].price").value(100));

    }

    @Test
    void whenGetApplianceById_thenReturnAppliance() throws Exception {

        Appliance appliance = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 100);
        when(applianceService.getApplianceById(1L)).thenReturn(appliance);

        mockMvc.perform(MockMvcRequestBuilders.get("/appliances/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.name").value("Fridge"))
               .andExpect(jsonPath("$.description").value("Fridge description"))
               .andExpect(jsonPath("$.brand").value("Fridge brand"))
               .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    void whenDeleteApplianceById_thenReturnAppliance() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/appliances/1"))
               .andExpect(status().isOk());

    }

    @Test
    void whenSaveAppliance_thenReturnAppliance() throws Exception {

        Appliance appliance = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 100);
        when(applianceService.saveAppliance(any(Appliance.class))).thenReturn(appliance);

        mockMvc.perform(post("/appliances")
                .contentType("application/json")
                .content("{\"id\":1,\"name\":\"Fridge\",\"description\":\"Fridge description\",\"brand\":\"Fridge brand\",\"price\":100}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.name").value("Fridge"))
               .andExpect(jsonPath("$.description").value("Fridge description"))
               .andExpect(jsonPath("$.brand").value("Fridge brand"))
               .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    void whenUpdateApplianceById_thenReturnAppliance() throws Exception {

        Appliance appliance = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 100);
        when(applianceService.updateAppliance(eq(1L), any(Appliance.class))).thenReturn(appliance);

        mockMvc.perform(MockMvcRequestBuilders.put("/appliances/1")
                .contentType("application/json")
                .content("{\"id\":1,\"name\":\"Fridge\",\"description\":\"Fridge description\",\"brand\":\"Fridge brand\",\"price\":100}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.name").value("Fridge"))
               .andExpect(jsonPath("$.description").value("Fridge description"))
               .andExpect(jsonPath("$.brand").value("Fridge brand"))
               .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    void whenFindNonExistentAppliance_thenReturn404() throws Exception {
        when(applianceService.getApplianceById(999L))
                .thenThrow(new ApplianceNotFoundException("Appliance not found"));

        mockMvc.perform(get("/appliances/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.message").value("Appliance not found"));
    }

    @Test
    void whenSaveApplianceWithInvalidData_thenReturn400() throws Exception {
        String invalidJson = "{\"name\":\"\", \"description\":\"\", \"brand\":\"\", \"price\":-100}";

        mockMvc.perform(post("/appliances")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson)).andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errors").exists())  // Verifica que existe el campo
               .andExpect(jsonPath("$.errors.name").value("Name is required"))  // Mensaje exacto
               .andExpect(jsonPath("$.errors.price").value("Price must be positive"));
    }
}
