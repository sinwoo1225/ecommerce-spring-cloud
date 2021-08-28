package siru.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siru.userservice.dto.UserDto;
import siru.userservice.service.UserService;
import siru.userservice.vo.RequestUser;
import siru.userservice.vo.ResponseUser;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UserController {

    private final Environment env;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return "It`s Working on User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = modelMapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

}
