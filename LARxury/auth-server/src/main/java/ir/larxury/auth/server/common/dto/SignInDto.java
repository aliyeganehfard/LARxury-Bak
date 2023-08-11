package ir.larxury.auth.server.common.dto;

import lombok.Data;

@Data
public class SignInDto {
    private String username;
    private String password;
}
