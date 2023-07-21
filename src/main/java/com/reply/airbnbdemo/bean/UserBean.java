package com.reply.airbnbdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserBean {

    private String email;
    private String userPassword;
    private Character gender;
    private String about;
    private String phone;
    private String address;
    private String fname;
    private String mInitial;
    private String lName;

    private String emEmail;
    private String emCountryCode;
    private String emPhone;
}
