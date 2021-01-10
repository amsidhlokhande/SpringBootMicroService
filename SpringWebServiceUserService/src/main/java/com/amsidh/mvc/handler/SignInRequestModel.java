package com.amsidh.mvc.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestModel {
    private static final long serialVersionUID = -1293645481714452901L;
    @Email(message = "Invalid emailId")
    @NotNull(message = "EmailId must not null or empty")
    private String username;
    @NotNull(message = "Password must not be null or empty")
    @Size(min = 8, max = 16, message = "Password length must be greater than 8 and less than 16")
    private String password;
}
