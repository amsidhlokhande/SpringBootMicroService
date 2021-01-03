package com.amsidh.mvc.repository.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERINFO")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = -313203031614115277L;

	@Id
	@Column(name = "USERID")
	private String userId;

	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;

	@Column(name = "EMAILID")
	private String emailId;

	@Column(name = "PASSWORD")
	private String encryptedPassword;

}
