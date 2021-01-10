package com.amsidh.mvc.repository.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = -313203031614115277L;

    @Id
    private String userId;

    private String firstName;

    private String lastName;

    private String emailId;

    private String encryptedPassword;

}
