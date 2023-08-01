package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.dto.PropertyDto;
import com.reply.airbnbdemo.exception.HostNotFoundException;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.model.Host;
import com.reply.airbnbdemo.model.Propertylisting;
import com.reply.airbnbdemo.repository.HostRepository;
import com.reply.airbnbdemo.repository.PropertyRepository;
import com.reply.airbnbdemo.repository.ReviewForPropertyRepository;
import com.reply.airbnbdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ReviewForPropertyRepository reviewForPropertyRepository;
    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final PropertyIncludedInWishListService propertyIncludedInWishListService;
    private final ModelMapper modelMapper;


    public void createProperty(PropertyBean propertyBean){
        Host host = getHost(propertyBean.getHostEmail());
        Propertylisting propertylisting = modelMapper.map(propertyBean, Propertylisting.class);
        propertylisting.setHid(host);
        propertylisting.setId(Objects.isNull(propertyRepository.getMaxId()) ? 1 : propertyRepository.getMaxId() + 1);

        propertyRepository.save(propertylisting);
    }

    @Transactional
    public void updatePropertyPricePerNight(BigDecimal newPrice, String propertyName){
        propertyRepository.updatePrice(propertyName, newPrice);
        propertyIncludedInWishListService.updateChangedFlag(propertyRepository.getByPropertyName(propertyName).getId());
    }


    public List<PropertyDto> getAllPropertiesByUserEmailOrCountry(String email, String country){
        validationService.validateEmailOrCountry(email, country);
        propertyRepository.getAllByUserEmailOrCountry(email, country);

        return propertyRepository.getAllByUserEmailOrCountry(email, country)
                .stream().map(obj -> modelMapper.map(obj, PropertyDto.class))
                .collect(Collectors.toList());

    }

    public List<PropertyDto> getAllProperties(){
        return propertyRepository.findAll().stream()
                .map(obj -> modelMapper.map(obj, PropertyDto.class))
                .collect(Collectors.toList());
    }

    public Propertylisting getPropertyByName(String propertyName){
        return propertyRepository.getByPropertyName(propertyName);
    }

    public PropertyDto getPropertyByNameDto(String propertyName){
        return modelMapper.map(propertyRepository.getByPropertyName(propertyName), PropertyDto.class);
    }

    public List<Propertylisting> getPropertiesByName(List<String> listOfNames){
        return propertyRepository.getAllByNames(listOfNames);
    }

    private Host getHost(String email){
        Airbnbuser user  = userRepository.findByEmail(email);

        if(Objects.isNull(user) || Objects.isNull(user.getHost())){
            throw new HostNotFoundException("No host Found");
        }
        return user.getHost();
    }

   // @Scheduled(fixedRate = 1000)
    public void calculateNumberOfRatingsAndAvgRating() {
        calculateNumberOfRatings();
        calculateAvgRating();
    }

    private void calculateNumberOfRatings() {
        System.out.println(propertyRepository.getNumberOfRatings());
    }

    private void calculateAvgRating() {
        System.out.println(reviewForPropertyRepository.getAvgRating());
    }
}
