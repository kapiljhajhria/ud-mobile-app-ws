package com.karg.userrsmanagement.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestModel {

    @NotBlank(message = "firstName cannot be empty")
    private String firstName;
    @NotBlank(message = "lastName cannot be empty")
    private String lastName;
    @NotBlank(message = "email cannot be empty")
    private String email;
    @NotBlank(message = "password cannot be empty")
    private String password;
}