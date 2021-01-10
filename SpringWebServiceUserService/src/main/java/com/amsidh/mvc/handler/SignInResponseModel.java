package com.amsidh.mvc.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponseModel {
    private final String jwtToken;
}
