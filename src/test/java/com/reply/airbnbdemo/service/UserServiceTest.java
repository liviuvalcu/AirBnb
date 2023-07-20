package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.enums.UserType;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.repository.BankAccountRepository;
import com.reply.airbnbdemo.repository.CreditCardRepository;
import com.reply.airbnbdemo.repository.HostRepository;
import com.reply.airbnbdemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HostRepository hostRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private ModelMapper modelMapper;



    @Test
    void setUserType() {
        when(userRepository.findById(1)).thenReturn(Optional.of(Airbnbuser.builder().build()));
        userService.setUserType(UserType.HOST, 1);
    }

    @Test
    void findByEmail(){
        when(userRepository.findByEmail(anyString())).thenReturn(Airbnbuser.builder().email("X").build());
        when(modelMapper.map(any(Airbnbuser.class), any())).thenReturn(UserDto.builder().email("X").build());
        UserDto dto = userService.findByEmail("X");

        assertNotNull(dto);
        assertEquals("X", dto.getEmail());
    }
}