package com.karg.userrsmanagement.shared.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private long id; //db id
    private String city;
    private String country;
    private String streetName;
    private String type;
    private UserDto userDetails;
}