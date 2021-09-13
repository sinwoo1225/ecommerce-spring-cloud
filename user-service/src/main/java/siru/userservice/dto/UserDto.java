package siru.userservice.dto;

import lombok.Data;
import siru.userservice.vo.ResponseOrder;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    private String email;

    private String name;

    private String pwd;

    private String userId;

    private Date createAt;

    private String encryptedPwd;

    private List<ResponseOrder> orderList;
}
