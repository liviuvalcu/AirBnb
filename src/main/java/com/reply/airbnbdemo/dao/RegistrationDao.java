package com.reply.airbnbdemo.dao;

import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.enums.UserType;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDao {
    private SignUpRequest signUpRequest;
    private UserBean userBean;
    private UserType userType;
    private PropertyBean propertyBean;
}
