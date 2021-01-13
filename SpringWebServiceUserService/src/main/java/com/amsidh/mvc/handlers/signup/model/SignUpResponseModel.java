package com.amsidh.mvc.handlers.signup.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SignUpResponseModel implements Serializable {

	private static final long serialVersionUID = -5450805146471897013L;

	private String userId;

	private String firstName;

	private String lastName;

	private String emailId;

}
