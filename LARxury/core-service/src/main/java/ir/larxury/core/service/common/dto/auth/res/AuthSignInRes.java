package ir.larxury.core.service.common.dto.auth.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthSignInRes implements Serializable {
    private String accessToken;
}
