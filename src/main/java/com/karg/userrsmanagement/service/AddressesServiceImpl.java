package com.karg.userrsmanagement.service;

import com.karg.userrsmanagement.entity.AddressEntity;
import com.karg.userrsmanagement.entity.UserEntity;
import com.karg.userrsmanagement.repository.AddressesRepository;
import com.karg.userrsmanagement.repository.UserRepository;
import com.karg.userrsmanagement.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressesServiceImpl implements AddressesService {

    @Autowired
    AddressesRepository addressesRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<AddressDto> getUserAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            return returnValue;
        }

        List<AddressEntity> addressEntities = addressesRepository.findAllByUserDetails(userEntity);

        for (AddressEntity addressEntity : addressEntities) {
            ModelMapper modelMapper = new ModelMapper();
            AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);
            returnValue.add(addressDto);
        }

        return returnValue;
    }

    @Override
    public AddressDto getUserAddress(String addressId) {
        ModelMapper modelMapper = new ModelMapper();
        AddressEntity addressEntity = addressesRepository.findByAddressId(addressId);

        if (addressEntity == null) {
            return null;
        }

        return modelMapper.map(addressEntity, AddressDto.class);
    }
}