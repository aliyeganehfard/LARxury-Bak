package ir.larxury.core.service.service.provider;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.common.utils.service.provider.AbstractProviderService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.common.dto.authService.req.AuthSignInReq;
import ir.larxury.core.service.common.dto.authService.res.AuthSignInRes;
import ir.larxury.core.service.common.dto.authService.res.UserIdRes;
import ir.larxury.core.service.service.provider.request.AuthHttpService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@PropertySource(value = "classpath:auth-service.properties")
@Service
public class AuthProvider extends AbstractProviderService {

    @Autowired
    private AuthHttpService authHttpService;

    @Value("${auth.service.username}")
    private String username;

    @Value("${auth.service.password}")
    private String password;

    public void setShopAdminRoleToUser(String userId) {
        var token = "Bearer " + getToken();
        var responseEntity = authHttpService.addShopAdmin(userId, token);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            super.validateResponse(generalResponse, null);
        } else {
            log.error("problem connection to dispatcher service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public Long getUserId(String username) {
        var token = "Bearer " + getToken();
        var responseEntity = authHttpService.findUserId(username, token);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            var userIdRes = super.validateResponse(generalResponse, UserIdRes.class);
            return userIdRes.getId();
        } else {
            log.error("problem connection to dispatcher service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected String login() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", username);
        form.add("password", password);
        var responseEntity = authHttpService.login(form);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            var authSignInRes = super.validateResponse(generalResponse, AuthSignInRes.class);
            return authSignInRes.getAccessToken();
        } else {
            log.error("problem connection to dispatcher service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}

