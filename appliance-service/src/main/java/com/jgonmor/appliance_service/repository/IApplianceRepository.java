package com.jgonmor.appliance_service.repository;

import com.jgonmor.appliance_service.model.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApplianceRepository extends JpaRepository<Appliance, Long> {

    List<Appliance> findByIdIn(List<Long> ids);
}
