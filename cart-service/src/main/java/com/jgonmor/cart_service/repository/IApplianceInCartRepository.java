package com.jgonmor.cart_service.repository;

import com.jgonmor.cart_service.model.ApplianceCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IApplianceInCartRepository extends JpaRepository<ApplianceCart, Long> {

    Optional<ApplianceCart> findByCartIdAndApplianceId(Long cartId, Long applianceId);

    List<ApplianceCart> findByCartId(Long cartId);

    @Transactional
    @Modifying
    boolean deleteByCartIdAndApplianceId(Long cartId, Long applianceId);
}
