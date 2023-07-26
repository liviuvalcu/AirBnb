package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.enums.DiscountLevels;
import com.reply.airbnbdemo.model.DiscountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiscountRepository extends JpaRepository<DiscountModel, Integer> {

    void deleteByDiscountLevels(DiscountLevels discountLevels);
    DiscountModel findByDiscountLevels(DiscountLevels discountLevels);

    @Query(value = """
                    select d.discount from discount d where 
                    d.minim_nights = (select max(ds.minim_nights) from discount ds where 
                    (ds.minim_nights <= :nightsStayed) OR 
                    (ds.minimal_amount_spent <= :amountSpent))
                    """, nativeQuery = true)
    Integer getDiscountByAmountOrNights(Integer amountSpent, Integer nightsStayed);
}
