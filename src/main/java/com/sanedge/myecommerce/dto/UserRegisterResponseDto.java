package com.sanedge.myecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterResponseDto {

    private UserLoginResponseDto user;
    private String error;
}
