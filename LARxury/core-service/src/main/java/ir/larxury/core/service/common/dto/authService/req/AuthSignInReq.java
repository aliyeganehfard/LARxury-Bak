package ir.larxury.core.service.common.dto.authService.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthSignInReq implements Serializable {
    private String username;
    private String password;
}
