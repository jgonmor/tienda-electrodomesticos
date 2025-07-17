package com.jgonmor.sell_service.service.sell;

import com.jgonmor.sell_service.dto.SellDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ISellService {

    SellDto saveSell(SellDto sell);

    SellDto getSellById(Long id);

    List<SellDto> getSellsByDay(LocalDate day);

}
