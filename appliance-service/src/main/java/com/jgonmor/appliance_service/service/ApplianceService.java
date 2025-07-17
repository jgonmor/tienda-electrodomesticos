package com.jgonmor.appliance_service.service;

import com.jgonmor.appliance_service.model.Appliance;
import com.jgonmor.appliance_service.repository.IApplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplianceService implements IApplianceService {

    @Autowired
    private IApplianceRepository applianceRepository;

    @Override
    public Appliance saveAppliance(Appliance appliance) {
        return applianceRepository.save(appliance);
    }

    @Override
    public Appliance getApplianceById(Long id) {
        return applianceRepository.findById(id).orElse(null);
    }

    @Override
    public Appliance updateAppliance(Long id,
                                     Appliance appliance) {

        Appliance applianceToUpdate = applianceRepository.getReferenceById(id);
        applianceToUpdate.setName(appliance.getName());
        applianceToUpdate.setDescription(appliance.getDescription());
        applianceToUpdate.setBrand(appliance.getBrand());
        applianceToUpdate.setPrice(appliance.getPrice());

        return applianceRepository.save(applianceToUpdate);
    }

    @Override
    public void deleteAppliance(Long id) {
        applianceRepository.deleteById(id);
    }

    @Override
    public List<Appliance> getAllAppliances() {
        return applianceRepository.findAll();
    }

    @Override
    public List<Appliance> getAllAppliancesInListId(List<Long> idList) {
        return applianceRepository.findByIdIn(idList);
    }
}
