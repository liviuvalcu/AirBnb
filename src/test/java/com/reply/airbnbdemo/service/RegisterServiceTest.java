package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.repository.PropertyListingRepository;
import com.reply.airbnbdemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @InjectMocks
    private RegisterService registerService;

    @Mock
    ModelMapper modelMapper;


    @Test
    void createRegister(){
        when(modelMapper.map(any(UserBean.class), any())).thenReturn(new Airbnbuser());
        registerService.createRegistrationDao(registerService.createRegistrationDaoForTest());

    }

}
