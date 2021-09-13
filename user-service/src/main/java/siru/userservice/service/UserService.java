package siru.userservice.service;


import siru.userservice.dto.UserDto;
import siru.userservice.jpa.UserEntity;

public interface UserService {

    UserDto createUser(UserDto user);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

}
