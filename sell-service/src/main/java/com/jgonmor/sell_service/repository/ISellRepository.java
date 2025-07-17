package com.jgonmor.sell_service.repository;

import com.jgonmor.sell_service.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ISellRepository extends JpaRepository<Sell, Long> {

    List<Sell> findAllByDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}
