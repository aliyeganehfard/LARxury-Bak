package ir.larxury.auth.server.common.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInDto {

    @NotEmpty(message = "username should not be empty!")
    private String username;

    @NotEmpty(message = "password should not be empty!")
    private String password;
}
