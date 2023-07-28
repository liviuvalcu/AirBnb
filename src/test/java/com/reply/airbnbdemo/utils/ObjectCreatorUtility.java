package com.reply.airbnbdemo.utils;

import java.util.Collections;
import java.util.List;

public class ObjectCreatorUtility {

    public final static String PROPERTY_NAME_1 = "PROPERTY_NAME_1";
    public final static String PROPERTY_NAME_2 = "PROPERTY_NAME_2";
    public final static String PROPERTY_NAME_3 = "PROPERTY_NAME_3";
    public final static String PROPERTY_NAME_4 = "PROPERTY_NAME_4";
    public final static String PROPERTY_NAME_5 = "PROPERTY_NAME_5";
    public final static String PROPERTY_NAME_6 = "PROPERTY_NAME_6";

    private static List<String> WISH_LIST_NAMES = List.of("Wishlist_1","Wishlist_2","Wishlist_3","Wishlist_4");
    private static List<String> PRIVACY = List.of("Y","N");
    private static final String userEmail = "test@gmail.com";

    public static List<String> getListOfPropertyNames(){
        return List.of(PROPERTY_NAME_1,PROPERTY_NAME_2,PROPERTY_NAME_3,PROPERTY_NAME_4,PROPERTY_NAME_5,PROPERTY_NAME_6);
    }

    public static String getWishListName(){
        int index = (int)(Math.random() * WISH_LIST_NAMES.size());
        return WISH_LIST_NAMES.get(index);
    }

    public static String getPrivacy(){
        int index = (int)(Math.random() * PRIVACY.size());
        return PRIVACY.get(index);
    }

    public static String getUserEmail(){
        return userEmail;
    }
}
