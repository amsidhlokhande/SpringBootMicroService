package com.amsidh.mvc.model.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AlbumResponseModel implements Serializable {

	private static final long serialVersionUID = -4959983519903876710L;
	private String albumId;
	private String albumName;
	private Double albumPrice;
	private String userId;
}
