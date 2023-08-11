package ir.larxury.auth.server.common.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
