package com.amsidh.mvc.feign.client.album.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
