package ir.larxury.core.service.service.provider.request;

import ir.larxury.common.utils.common.dto.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "http://localhost:8080/")
public interface AuthHttpService {

    @PostExchange(value = "auth/signIn", contentType = "application/x-www-form-urlencoded")
    ResponseEntity<GeneralResponse> login(@RequestBody MultiValueMap<String, String> loginForm);

    @PostExchange("v1/user/find/userId")
    ResponseEntity<GeneralResponse> findUserId(@RequestParam("username") String username,
                               @RequestHeader("Authorization") String token);

    @PostExchange("v1/user/role/add/shopAdmin")
    ResponseEntity<GeneralResponse> addShopAdmin(@RequestParam("userId") String userId,
                               @RequestHeader("Authorization") String token);
}
