package ir.larxury.core.service.common.dto.authService.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoRes implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
