package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.dto.DiscountDto;
import com.reply.airbnbdemo.enums.DiscountLevels;
import com.reply.airbnbdemo.model.DiscountModel;
import com.reply.airbnbdemo.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public void createDiscount(String name, Integer minimNights, BigDecimal minimalAmountSpent, DiscountLevels discountLevels){
        DiscountModel discountToCreate = DiscountModel
                .builder()
                .name(name)
                .minimNights(minimNights)
                .minimalAmountSpent(minimalAmountSpent)
                .discountLevels(discountLevels)
                .build();

        discountRepository.save(discountToCreate);
    }

    @Transactional
    public void updateDiscount(String name, Integer minimNights, BigDecimal minimalAmountSpent, DiscountLevels discountLevels){

        DiscountModel model = discountRepository.findByDiscountLevels(discountLevels);
        model.setName(name);
        model.setMinimNights(minimNights);
        model.setMinimalAmountSpent(minimalAmountSpent);


        discountRepository.save(model);
    }

    @Transactional
    public void deleteDiscount(DiscountLevels discountLevels){
        discountRepository.deleteByDiscountLevels(discountLevels);
    }

    public List<DiscountDto> getAll(){
     return  discountRepository.findAll().stream().map(model -> modelMapper.map(model, DiscountDto.class)).collect(Collectors.toList());
    }

    public Integer getDiscount(Integer amountSpent, Integer nightsStayed){
        return discountRepository.getDiscountByAmountOrNights(amountSpent, nightsStayed);
    }
}
