package ir.larxury.auth.service.common.dto.user.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserReq implements Serializable {
    private String username;
}
