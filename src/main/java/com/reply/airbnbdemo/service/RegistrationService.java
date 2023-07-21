package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.dao.JwtAuthenticationResponse;
import com.reply.airbnbdemo.dao.RegistrationDao;
import com.reply.airbnbdemo.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PropertyService propertyService;


    @Transactional
    public JwtAuthenticationResponse registerUser(RegistrationDao registrationDao){
        JwtAuthenticationResponse responseToken =  authenticationService.signup(registrationDao.getSignUpRequest());

        fillMandatoryFields(registrationDao);
        Integer userId = userService.insertUser(registrationDao.getUserBean());
        userService.setUserType(registrationDao.getUserType(), userId);

        if(UserType.HOST.equals(registrationDao.getUserType())){
            propertyService.createProperty(registrationDao.getPropertyBean());
        }
        return responseToken;
    }

    private void fillMandatoryFields(RegistrationDao registrationDao){
        registrationDao.getUserBean().setEmEmail(registrationDao.getSignUpRequest().getEmail());
        registrationDao.getUserBean().setEmPhone(registrationDao.getUserBean().getPhone());
        registrationDao.getUserBean().setFname(registrationDao.getSignUpRequest().getFirstName());
        registrationDao.getUserBean().setEmail(registrationDao.getSignUpRequest().getEmail());
        registrationDao.getPropertyBean().setHostEmail(registrationDao.getSignUpRequest().getEmail());
        registrationDao.getPropertyBean().setZipcode(300);
        registrationDao.getUserBean().setUserPassword("******");
    }
}
