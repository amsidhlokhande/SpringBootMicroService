package com.amsidh.mvc.handler.user.model;

import java.io.Serializable;
import java.util.List;

import com.amsidh.mvc.feign.client.album.model.AlbumResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String firstName;

    private String lastName;

    private String emailId;

    private List<AlbumResponseModel> albums;

}
