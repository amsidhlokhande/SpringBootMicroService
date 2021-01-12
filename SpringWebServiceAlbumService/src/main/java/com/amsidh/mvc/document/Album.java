package com.amsidh.mvc.document;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Document("albums")
public class Album implements Serializable {

	private static final long serialVersionUID = -4959983519903876710L;
	@Id
	private String albumId;
	private String albumName;
	private Double albumPrice;
	@NotNull
	private String userId;
}
