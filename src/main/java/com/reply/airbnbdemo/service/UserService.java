package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.dao.RegistrationDao;
import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.enums.Role;
import com.reply.airbnbdemo.enums.UserType;
import com.reply.airbnbdemo.exception.ResourceAlreadyExistsException;
import com.reply.airbnbdemo.model.*;
import com.reply.airbnbdemo.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
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

    @Autowired
    PropertyRepository propertyRepository;


    public UserDto findByEmail(String email) {
        Airbnbuser user = userRepository.findByEmail(email).get();
        UserDto dto = modelMapper.map(user, UserDto.class);
        return dto;
    }

    public Airbnbuser findByEmailEntity(String email) {
        return userRepository.findByEmail(email).get();
    }

    public UserDto findByFnameAndLName(String fname, String lName) {
        return modelMapper.map(userRepository.findByFnameAndLName(fname, lName).get(), UserDto.class);
    }

    @Transactional
    public void insertUser(UserBean beanUser) {
        Airbnbuser user = modelMapper.map(beanUser, Airbnbuser.class);
        user.setCreated(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Integer id) {
        userRepository.deleteAirbnbuserById(id);
    }

    @Transactional
    public void setUserType(UserType userType, Integer userId) {
        switch (userType) {
            case HOST -> createHost(userId);
            case GUEST -> createGuest(userId);
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
                .id(3)
                .accountType("T1")
                .routingNum(123)
                .build();

        bankAccountRepository.saveAndFlush(bankaccount);
        host.setBankAccountNumber(bankaccount);
        host.setAirbnbuser(user);
        hostRepository.save(host);
    }

    public void register(RegistrationDao registrationDao) {

        var email = registrationDao.getUserBean().getEmail();
        userRepository.findByEmail(email)
                .map(existingUser -> {
                    throw new ResourceAlreadyExistsException("user with email " + email + " already exists");
                }).orElseGet(() -> saveUser(registrationDao));


    }

    private Airbnbuser saveUser(RegistrationDao dao) {

        var user = dao.getUserBean();
        var type = dao.getUserType();
        var property = dao.getPropertyBean();

        Airbnbuser userAir = modelMapper.map(user, Airbnbuser.class);
        Integer id = userAir.getId();
        Propertylisting propertylisting = modelMapper.map(property, Propertylisting.class);

        userRepository.save(userAir);
        setUserType(type, id);

        if (!Objects.isNull(propertylisting)) {
            propertyRepository.save(propertylisting);
        }


        return userAir;
    }
}
