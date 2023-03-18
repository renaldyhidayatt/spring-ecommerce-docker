package com.sanedge.myecommerce.dto;

import com.sanedge.myecommerce.auth.user.ApplicationUserRole;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotNull
    private String username;

    @NotNull
    @Min(value = 4, message = "Password must be longer than 4.")
    private String password;

    @NotNull
    private ApplicationUserRole role;
}
