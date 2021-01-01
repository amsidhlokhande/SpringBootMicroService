package com.amsidh.mvc.model.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage implements Serializable{
	private static final long serialVersionUID = -2244341596809278764L;
	private Date timestamp;
	private String message;

}
