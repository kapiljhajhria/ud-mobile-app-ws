package com.example.photoapp.service;

import com.example.photoapp.shared.dto.AddressDto;

import java.util.List;

public interface AddressesService {
    List<AddressDto> getUserAddresses(String userId);

    AddressDto getUserAddress(String addressId);
}