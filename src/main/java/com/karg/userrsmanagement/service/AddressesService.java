package com.karg.userrsmanagement.service;

import com.karg.userrsmanagement.shared.dto.AddressDto;

import java.util.List;

public interface AddressesService {
    List<AddressDto> getUserAddresses(String userId);

    AddressDto getUserAddress(String addressId);
}