package com.karg.userrsmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serial;

@Entity(name = "users")
@Getter
@Setter
public class UserEntity implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;

    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;
}