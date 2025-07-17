package com.jgonmor.sell_service.service.sell;

import com.jgonmor.sell_service.dto.CartDto;
import com.jgonmor.sell_service.dto.SellDto;
import com.jgonmor.sell_service.model.Sell;
import com.jgonmor.sell_service.repository.ICartApi;
import com.jgonmor.sell_service.repository.ISellRepository;
import com.jgonmor.sell_service.service.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SellService implements ISellService {

    @Autowired
    ISellRepository sellRepository;

    @Autowired
    ICartService cartService;


    @Override
    public SellDto saveSell(SellDto sell) {

        SellDto response = new SellDto();
        Sell repositoryResponse = sellRepository.save(sell.toModel());

        response.setId(repositoryResponse.getId());
        response.setDate(repositoryResponse.getDate());
        response.setCart(cartService.getCartById(repositoryResponse.getCartId()));

        return response;
    }

    @Override
    public SellDto getSellById(Long id) {

        SellDto response = new SellDto();
        Sell repositoryResponse = sellRepository.findById(id).orElse(null);
        response.setId(repositoryResponse.getId());
        response.setDate(repositoryResponse.getDate());
        response.setCart(cartService.getCartById(repositoryResponse.getCartId()));
        return response;
    }

    @Override
    public List<SellDto> getSellsByDay(LocalDate day) {

        LocalDateTime start = LocalDateTime.of(day, LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(1).minusSeconds(1);

        //TODO Add getById in cart-service to request a list in a single connection
        List<SellDto> response = sellRepository.findAllByDateBetween(start, end).parallelStream().map(
                sell -> {
                    SellDto sellDto = new SellDto();
                    sellDto.setId(sell.getId());
                    sellDto.setDate(sell.getDate());
                    sellDto.setCart(cartService.getCartById(sell.getCartId()));

                    return sellDto;
                }
        ).toList();

        return response;
    }
}
