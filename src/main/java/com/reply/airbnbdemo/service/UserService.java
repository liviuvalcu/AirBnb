package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.enums.UserType;
import com.reply.airbnbdemo.model.*;
import com.reply.airbnbdemo.repository.BankAccountRepository;
import com.reply.airbnbdemo.repository.CreditCardRepository;
import com.reply.airbnbdemo.repository.HostRepository;
import com.reply.airbnbdemo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class UserService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private HostRepository hostRepository;

   @Autowired
   private BankAccountRepository bankAccountRepository;

   @Autowired
   private CreditCardRepository creditCardRepository;

   @Autowired
   private ModelMapper modelMapper;

   public UserDto findByEmail(String email){
      Airbnbuser user = userRepository.findByEmail(email);
       UserDto dto = modelMapper.map(user, UserDto.class);
       return dto;
   }

   public UserDto findByFnameAndLName(String fname, String lName){
        return modelMapper.map(userRepository.findByFnameAndLName(fname, lName).get(), UserDto.class);
   }

   @Transactional
   public void insertUser(UserBean beanUser){
       Airbnbuser user = modelMapper.map(beanUser, Airbnbuser.class);
       user.setCreated(LocalDateTime.now().toInstant(ZoneOffset.UTC));
       userRepository.save(user);
   }

   @Transactional
   public void deleteUserById(Integer id){
       userRepository.deleteAirbnbuserById(id);
   }

   @Transactional
    public void setUserType( UserType userType, Integer userId){
       switch (userType){
           case HOST -> createHost(userId);
           case GUEST -> createGuest(userId) ;
       }
    }

    private void createGuest(Integer userId) {
       Airbnbuser user = userRepository.findById(userId).get();

        Guest guest = Guest.builder().build();
        Creditcard creditcard = Creditcard.builder()
                .csv(001)
                .expirationDate(LocalDate.now())
                .cardholderName(user.getLName() + " " + user.getFname())
                .cardType("VISA")
                .build();

        guest.setCreditCardNum(creditcard);
        user.setGuest(guest);
        userRepository.save(user);

    }

    private void createHost(Integer userId) {
        Airbnbuser user = userRepository.findById(userId).get();

        Host host = Host.builder().build();
        Bankaccount bankaccount = Bankaccount.builder()
                .id(1)
                .accountType("T1")
                .routingNum(123)
                .build();

        bankAccountRepository.saveAndFlush(bankaccount);
        host.setBankAccountNumber(bankaccount);
        host.setAirbnbuser(user);
        hostRepository.save(host);
    }

}
