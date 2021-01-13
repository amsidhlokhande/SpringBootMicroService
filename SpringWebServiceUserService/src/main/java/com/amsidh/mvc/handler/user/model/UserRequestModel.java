package com.amsidh.mvc.handler.user.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "First name must not be null or empty")
    @Size(min = 2, message = "First name length must be greater than 2")
    private String firstName;

    @NotNull(message = "Last name must not be null or empty")
    @Size(min = 2, message = "Last name length must be greater than 2")
    private String lastName;

    @Email(message = "Invalid emailId")
    @NotNull(message = "EmailId must not null or empty")
    private String emailId;

}
