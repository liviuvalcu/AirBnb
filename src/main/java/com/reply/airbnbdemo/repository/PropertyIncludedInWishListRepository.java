package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Propertyincludedinwishlist;
import com.reply.airbnbdemo.model.id.PropertyincludedinwishlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PropertyIncludedInWishListRepository extends JpaRepository<Propertyincludedinwishlist, PropertyincludedinwishlistId> {


    @Query(value = "update Propertyincludedinwishlist p set p.flag = (case when p.flag = true then false else true end) where p.pid.id= :propertyId")
    @Modifying
    void updateModifiedFlag(@Param("propertyId")Integer propertyId );

    @Query(value = """
        INSERT INTO propertyincludedinwishlist 
        (PID, AirBnBUID, WishlistName, CheckInDate, CheckOutDate, modifiedFlag) VALUES 
        (:pid, :airBnBUID, :wishlistName, :checkInDate, :checkOutDate, :flag) 
        """, nativeQuery = true)
    @Modifying
    void insertProperty(@Param("pid") Integer pid,
                        @Param("airBnBUID") Integer airBnBUID,
                        @Param("wishlistName") String wishlistName,
                        @Param("checkInDate") LocalDate checkInDate,
                        @Param("checkOutDate") LocalDate checkOutDate,
                        @Param("flag") boolean flag);

    @Query(value = "select p from Propertyincludedinwishlist  p where p.airBnBUID.airbnbuser.email = :userName and p.id.wishlistName = :name")
    List<Propertyincludedinwishlist> getAllByUserNameAndName(@Param("userName") String userName, @Param("name") String name);
}
