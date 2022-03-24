package com.example.photoapp.service;

import com.example.photoapp.entity.AddressEntity;
import com.example.photoapp.entity.UserEntity;
import com.example.photoapp.repository.AddressesRepository;
import com.example.photoapp.repository.UserRepository;
import com.example.photoapp.shared.dto.AddressDto;
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