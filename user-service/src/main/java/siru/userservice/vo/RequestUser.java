package siru.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class RequestUser {

    @NotNull(message = "email can not be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "name can not be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @NotNull(message = "password can not be null")
    @Size(min = 8, message = "password must be equal or grater than 8 characters")
    private String pwd;
}
