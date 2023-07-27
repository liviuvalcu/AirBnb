package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Wishlist;
import com.reply.airbnbdemo.model.id.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishListRepository extends JpaRepository<Wishlist, WishlistId> {

    @Query(value = "update Wishlist w set w.id.wishlistName = :newWishListName where w.id.wishlistName= :wishListName and w.id.airBnBUID= :userId")
    @Modifying
    void updateWishListName(@Param("wishListName") String wishListName, @Param("userId") Integer userId, @Param("newWishListName") String newWishListName);


    @Query(value = "update Wishlist w set w.privacy = :privacy where w.id.wishlistName= :wishListName and w.id.airBnBUID= :userId")
    @Modifying
    void updateWishPrivacy(@Param("wishListName") String wishListName, @Param("userId") Integer userId, @Param("privacy") Character privacy);

    @Query(value = "select w from Wishlist w where w.airBnBUID.airbnbuser.email= :username")
    List<Wishlist> findAllByUserName(@Param("username") String username);

    void deleteById(@Param("id") WishlistId id);
}
