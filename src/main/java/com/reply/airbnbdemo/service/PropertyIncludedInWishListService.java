package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.model.Propertyincludedinwishlist;
import com.reply.airbnbdemo.model.Propertylisting;
import com.reply.airbnbdemo.model.Wishlist;
import com.reply.airbnbdemo.model.id.PropertyincludedinwishlistId;
import com.reply.airbnbdemo.repository.PropertyIncludedInWishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyIncludedInWishListService {

    private final PropertyIncludedInWishListRepository propertyIncludedInWishListRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void addPropertyInWishList(Wishlist wishlist, List<Propertylisting> properties){

        properties.stream().forEach(property -> {
            PropertyincludedinwishlistId id = PropertyincludedinwishlistId
                    .builder()
                    .airBnBUID(wishlist.getAirBnBUID().getId())
                    .wishlistName(wishlist.getId().getWishlistName())
                    .pid(property.getId())
                    .build();
            Propertyincludedinwishlist propertyincludedinwishlist = Propertyincludedinwishlist.builder()
                    .airBnBUID(wishlist.getAirBnBUID())
                    .id(id)
                    .flag(false)
                    .pid(property)
                    .wishlistName(wishlist)
                    .build();
          //  propertyIncludedInWishListRepository.saveAndFlush(propertyincludedinwishlist);
           // propertyIncludedInWishListRepository.insertProperty(property.getId(), wishlist.getAirBnBUID().getId(), wishlist.getId().getWishlistName(), null, null, false);

            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO propertyincludedinwishlist (CheckInDate,CheckOutDate,modifiedFlag,AirBnBUID,PID,WishlistName) VALUES (?, ?, ?, ?, ?, ?)");
                    preparedStatement.setDate(1,  java.sql.Date.valueOf(LocalDate.now()));
                    preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                    preparedStatement.setBoolean(3, false);
                    preparedStatement.setInt(4, wishlist.getAirBnBUID().getId());
                    preparedStatement.setInt(5, property.getId());
                    preparedStatement.setString(6, wishlist.getId().getWishlistName());
                    return preparedStatement;
                }
            });

        });

    }

    public void addNewProperties(Wishlist wishlist, List<Propertylisting> properties){

        properties.stream().forEach(property -> {
            PropertyincludedinwishlistId id = PropertyincludedinwishlistId
                    .builder()
                    .pid(property.getId())
                    .airBnBUID(wishlist.getAirBnBUID().getId())
                    .wishlistName(wishlist.getId().getWishlistName())
                    .build();
            Propertyincludedinwishlist propertyincludedinwishlist = propertyIncludedInWishListRepository.getReferenceById(id);
            propertyIncludedInWishListRepository.saveAndFlush(propertyincludedinwishlist);
        });

    }

    @Transactional
    public void updateChangedFlag(Integer propertyId){
        propertyIncludedInWishListRepository.updateModifiedFlag(propertyId);
    }

}
