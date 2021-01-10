package com.amsidh.mvc.model.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDto implements Serializable {
    private static final long serialVersionUID = -2172716244865095452L;
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
}
