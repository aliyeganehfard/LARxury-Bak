package ir.larxury.core.service.provider.request;

import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.core.service.common.dto.auth.req.AuthSignInReq;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(accept = "application/json", contentType = "application/json")
public interface AuthProvider {

    @PostExchange("auth/signIn")
    GeneralResponse login(@RequestBody AuthSignInReq req);

    @PostExchange("v1/user/find/userId")
    GeneralResponse findUserId(@RequestParam("username") String username,
                               @RequestHeader("Authorization") String token);

    @PostExchange("v1/user/role/add/shopAdmin")
    GeneralResponse addShopAdmin(@RequestParam("userId") String userId,
                               @RequestHeader("Authorization") String token);
}
