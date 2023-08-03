package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.enums.DiscountLevels;
import com.reply.airbnbdemo.model.DiscountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiscountRepository extends JpaRepository<DiscountModel, Integer> {

    void deleteByDiscountLevels(DiscountLevels discountLevels);
    DiscountModel findByDiscountLevels(DiscountLevels discountLevels);
    DiscountModel findByName(String name);

    @Query(value = """
                    select d.discount from discount d where 
                    d.minimNights = (select max(ds.minimNights) from discount ds where 
                    (ds.minimNights <= :nightsStayed) OR 
                    (ds.minimalAmountSpent <= :amountSpent))
                    """, nativeQuery = true)
    Integer getDiscountByAmountOrNights(Integer amountSpent, Integer nightsStayed);
}
