package com.jgonmor.sell_service.controller;

import com.jgonmor.sell_service.dto.SellDto;
import com.jgonmor.sell_service.service.sell.ISellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sell")
public class SellController {

    @Autowired
    private ISellService sellService;

    @GetMapping("/{id}")
    public SellDto getSellById(@PathVariable Long id){
        return sellService.getSellById(id);
    }

    @PostMapping
    public SellDto saveSell(@RequestBody SellDto sell){
        return sellService.saveSell(sell);
    }


    @GetMapping("/byDay/{day}")
    public List<SellDto> getSellsByDay(@PathVariable LocalDate day){
        return sellService.getSellsByDay(day);
    }
}
