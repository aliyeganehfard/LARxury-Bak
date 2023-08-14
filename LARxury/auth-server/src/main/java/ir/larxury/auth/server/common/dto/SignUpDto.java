package ir.larxury.auth.server.common.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpDto implements Serializable {

    @NotEmpty(message = "username should not be empty!")
    private String username;

    @Pattern(regexp = "^.{8,}$",message = "password should not be less than 8 characters!")
    private String password;

    @Pattern(regexp = "^.{8,}$",message = "confirm password should not be less than 8 characters!")
    private String confirmPassword;

    @NotEmpty(message = "firstname should not be empty!")
    private String firstname;

    @NotEmpty(message = "lastname should not be empty!")
    private String lastname;

    @Pattern(regexp = "^09+[0-9]{9}$", message = "in valid phone number!")
    private String phoneNumber;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2}$", message = "invalid email address!")
    private String email;
}
