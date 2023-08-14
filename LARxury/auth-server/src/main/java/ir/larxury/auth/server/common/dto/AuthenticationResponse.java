package ir.larxury.auth.server.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationResponse implements Serializable {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
