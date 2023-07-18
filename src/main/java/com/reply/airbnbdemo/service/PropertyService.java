package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.dto.PropertyDto;
import com.reply.airbnbdemo.exception.HostNotFoundException;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.model.Host;
import com.reply.airbnbdemo.model.Propertylisting;
import com.reply.airbnbdemo.repository.HostRepository;
import com.reply.airbnbdemo.repository.PropertyRepository;
import com.reply.airbnbdemo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ModelMapper modelMapper;


    public void createProperty(PropertyBean propertyBean){
        Host host = getHost(propertyBean.getHostEmail());
        Propertylisting propertylisting = modelMapper.map(propertyBean, Propertylisting.class);
        propertylisting.setHid(host);
        propertylisting.setId(Objects.isNull(propertyRepository.getMaxId()) ? 1 : propertyRepository.getMaxId() + 1);

        propertyRepository.save(propertylisting);
    }

    public List<PropertyDto> getAllPropertiesByUserEmailOrCountry(String email, String country){
        validationService.validateEmailOrCountry(email, country);
        propertyRepository.getAllByUserEmailOrCountry(email, country);

        return propertyRepository.getAllByUserEmailOrCountry(email, country)
                .stream().map(obj -> modelMapper.map(obj, PropertyDto.class))
                .collect(Collectors.toList());

    }

    public Propertylisting getPropertyByName(String propertyName){
        return propertyRepository.getByPropertyName(propertyName);
    }

    private Host getHost(String email){
        Airbnbuser user  = userRepository.findByEmail(email);

        if(Objects.isNull(user) || Objects.isNull(user.getHost())){
            throw new HostNotFoundException("No host Found");
        }
        return user.getHost();
    }
}
