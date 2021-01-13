package com.amsidh.mvc.handler.signin.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseModel implements Serializable {
	private static final long serialVersionUID = -1360060476153550928L;
	private String jwtToken;
}
