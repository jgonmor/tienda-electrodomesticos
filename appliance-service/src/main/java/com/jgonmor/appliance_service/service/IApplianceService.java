package com.jgonmor.appliance_service.service;

import com.jgonmor.appliance_service.model.Appliance;

import java.util.List;

public interface IApplianceService {

    Appliance saveAppliance(Appliance appliance);

    Appliance getApplianceById(Long id);

    Appliance updateAppliance(Long id, Appliance appliance);

    void deleteAppliance(Long id);

    List<Appliance> getAllAppliances();

    List<Appliance> getAllAppliancesInListId(List<Long> idList);
}
