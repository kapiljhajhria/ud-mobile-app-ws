package com.example.photoapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "password_reset_token")
public class PasswordResetTokenEntity implements java.io.Serializable {


    @Serial
    private static final long serialVersionUID = 8390790796475442634L;

    @Id
    @GeneratedValue
    private Long id;

    //one token can be associated with one user only
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String token;

}