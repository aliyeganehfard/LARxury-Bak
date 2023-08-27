package ir.larxury.core.service.provider;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.provider.AbstractProviderService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.common.dto.authService.res.AuthSignInRes;
import ir.larxury.core.service.common.dto.authService.res.UserIdRes;
import ir.larxury.core.service.common.dto.authService.res.UserInfoRes;
import ir.larxury.core.service.provider.request.AuthServiceHttpProvider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Service
@PropertySource(value = "classpath:auth-service.properties")
public class AuthServiceProvider extends AbstractProviderService {

    @Autowired
    private AuthServiceHttpProvider authServiceHttpProvider;

    @Value("${auth.service.username}")
    private String username;

    @Value("${auth.service.password}")
    private String password;

    public void setShopAdminRoleToUser(String userId) {
        var token = "Bearer " + getToken();
        var responseEntity = authServiceHttpProvider.addShopAdmin(userId, token);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            super.validateAndGetResponse(generalResponse, null);
        } else {
            log.error("problem connection to auth service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CONNECTION_ERROR);
        }
    }

    public UserInfoRes getUserInfo(String userId) {
        var token = "Bearer " + getToken();
        var responseEntity = authServiceHttpProvider.findUserInfo(userId, token);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            return super.validateAndGetResponse(generalResponse, UserInfoRes.class);
        } else {
            log.error("problem connection to auth service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CONNECTION_ERROR);
        }
    }

    public Long getUserId(String username) {
        var token = "Bearer " + getToken();
        var responseEntity = authServiceHttpProvider.findUserId(username, token);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            var userIdRes = super.validateAndGetResponse(generalResponse, UserIdRes.class);
            return userIdRes.getId();
        } else {
            log.error("problem connection to auth service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CONNECTION_ERROR);
        }
    }

    @Override
    protected String login() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", username);
        form.add("password", password);
        var responseEntity = authServiceHttpProvider.login(form);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            var authSignInRes = super.validateAndGetResponse(generalResponse, AuthSignInRes.class);
            return authSignInRes.getAccessToken();
        } else {
            log.error("problem connection to auth service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CONNECTION_ERROR);
        }
    }

}

