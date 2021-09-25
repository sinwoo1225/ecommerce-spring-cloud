package siru.userservice.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import siru.userservice.dto.UserDto;
import siru.userservice.jpa.UserEntity;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);
}
