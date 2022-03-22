package com.karg.userrsmanagement.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressesRest {
    private String addressId;//public id for the clients
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
}