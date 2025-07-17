package com.jgonmor.cart_service.repository;

import com.jgonmor.cart_service.dto.ApplianceDTO;

import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "appliance-service" , url = "${APPLIANCE_SERVICE_URL}")
public interface IApplianceApi {

    @GetMapping("/{id}")
    public ApplianceDTO getApplianceById(@PathVariable Long id);

    @GetMapping("/idList/{idList}")
    public List<ApplianceDTO> getAppliancesInListId(@PathVariable List<Long> idList);

}
