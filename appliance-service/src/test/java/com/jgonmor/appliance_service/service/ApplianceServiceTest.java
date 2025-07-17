package com.jgonmor.appliance_service.service;

import com.jgonmor.appliance_service.model.Appliance;
import com.jgonmor.appliance_service.repository.IApplianceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ApplianceServiceTest {

    @Mock
    private IApplianceRepository applianceRepository;

    @InjectMocks
    private ApplianceService applianceService;

    @Test
    void whenSaveAppliance_thenReturnAppliance() {

        Appliance applianceToSave = new Appliance(null, "Fridge", "Fridge description", "Fridge brand", 100);
        Appliance applianceSaved = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 100);

        when(applianceRepository.save(applianceToSave)).thenReturn(applianceSaved);

        Appliance result = applianceService.saveAppliance(applianceToSave);

        assert result.equals(applianceSaved);

    }

    @Test
    void whenGetApplianceById_thenReturnAppliance() {
        Appliance appliance = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 100);

        when(applianceRepository.findById(1L)).thenReturn(Optional.of(appliance));

        Appliance result = applianceService.getApplianceById(1L);

        assert result.equals(appliance);

    }

    @Test
    void whenUpdateAppliance_thenReturnAppliance() {

        Appliance appliance = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 100);
        Appliance applianceToUpdate = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 200);
        Appliance applianceUpdated = new Appliance(1L, "Fridge", "Fridge description", "Fridge brand", 200);

        when(applianceRepository.getReferenceById(1L)).thenReturn(applianceToUpdate);
        when(applianceRepository.save(applianceToUpdate)).thenReturn(applianceUpdated);

        Appliance result = applianceService.updateAppliance(1L, appliance);

        assert result.equals(applianceUpdated);

    }

    @Test
    void whenDeleteAppliance_thenReturnAppliance() {
        applianceService.deleteAppliance(1L);
    }

}
