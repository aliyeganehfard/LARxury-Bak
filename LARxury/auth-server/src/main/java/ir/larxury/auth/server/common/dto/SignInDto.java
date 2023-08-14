package ir.larxury.auth.server.common.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SignInDto implements Serializable {

    @NotEmpty(message = "username should not be empty!")
    private String username;

    @NotEmpty(message = "password should not be empty!")
    private String password;
}
