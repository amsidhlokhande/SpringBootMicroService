package com.amsidh.mvc.repository.user.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<UserEntity, String> {

	UserEntity findByEmailId(String userName);

}
