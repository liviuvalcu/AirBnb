package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.dao.RegistrationDao;
import com.reply.airbnbdemo.dao.SignUpRequest;
import com.reply.airbnbdemo.enums.Role;
import com.reply.airbnbdemo.enums.UserType;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.model.Propertylisting;
import com.reply.airbnbdemo.model.auth.User;
import com.reply.airbnbdemo.repository.PropertyListingRepository;
import com.reply.airbnbdemo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Service
public class RegisterService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PropertyListingRepository propertyListingRepository;

    @Autowired
    UserService userService;

    public void createRegistrationDao(RegistrationDao registrationDao){
        if(userRepository.findByFnameAndLName(registrationDao.getUserBean().getLName(),registrationDao.getUserBean().getFname()).isEmpty()) {
            Airbnbuser user = modelMapper.map(registrationDao.getUserBean(), Airbnbuser.class);
            user.setFname(registrationDao.getSignUpRequest().getFirstName());
            user.setLName(registrationDao.getSignUpRequest().getLastName());
            user.setUserPassword(registrationDao.getSignUpRequest().getPassword());
            user.setEmail(registrationDao.getSignUpRequest().getEmail());

            userRepository.saveAndFlush(user);
            userService.setUserType(registrationDao.getUserType(), user.getId());

            if (registrationDao.getUserType().equals(UserType.HOST)) {
                Propertylisting propertylisting = modelMapper.map(registrationDao.getPropertyBean(), Propertylisting.class);
                propertyListingRepository.save(propertylisting);
            }

        }else{
            System.out.println("User already exist");
        }
    }

    public RegistrationDao createRegistrationDaoForTest(){
        RegistrationDao registrationDao =RegistrationDao.builder()
                .signUpRequest(new SignUpRequest("Dan", "Serpe", "srobert7@gmail.com", "12345"))
                .userBean(new UserBean("srobert7@gmail.com", "12345",
                        'M',"about", "0741123", "address", "Robert", "R", "Serpe", "emEmail", "2","emPhone"))
                .userType(UserType.HOST)
                .propertyBean(new PropertyBean("CasaNoastra",12, 2, 2, 2, BigDecimal.ONE, BigDecimal.ONE, LocalDate.now(), Instant.now(), Instant.now(), 'G', 2, "No", BigDecimal.TEN, "Magic", "Franta", "Da", "Franta", BigDecimal.ONE, "srobert7@gmail.com"))
                .build();
        return registrationDao;
    }
}
