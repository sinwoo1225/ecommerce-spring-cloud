package siru.userservice.service;


import siru.userservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);
}
