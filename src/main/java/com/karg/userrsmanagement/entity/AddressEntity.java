package com.karg.userrsmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;

@Getter
@Setter
@Entity(name = "addresses")
public class AddressEntity implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = 7809200551672852690L;

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 30, nullable = false)
    private String addressId;

    @Column(length = 15, nullable = false)
    private String city;

    @Column(length = 15, nullable = false)
    private String country;

    @Column(length = 100, nullable = false)
    private String streetName;

    @Column(length = 7, nullable = false)
    private String postalCode;

    @Column(length = 10, nullable = false)
    private String type;
    //many addresses can belong to one user
    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;//this userDetails should be provided as a name in UserEntity,
    // mappedBy will be equal to userDetails in UserEntity
}