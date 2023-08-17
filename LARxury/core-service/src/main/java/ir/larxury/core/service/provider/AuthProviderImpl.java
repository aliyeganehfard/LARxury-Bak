package ir.larxury.core.service.provider;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.common.dto.auth.req.AuthSignInReq;
import ir.larxury.core.service.common.dto.auth.res.AuthSignInRes;
import ir.larxury.core.service.common.dto.auth.res.UserIdRes;
import ir.larxury.core.service.provider.request.AuthProvider;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Slf4j
@PropertySource(value = "classpath:auth-service.properties")
@Service
public class AuthProviderImpl {

    @Autowired
    private AuthProvider authProvider;

    private final ModelMapper mapper = new ModelMapper();

    @Value("${auth.service.username}")
    private String username;

    @Value("${auth.service.password}")
    private String password;

    public Long getUserId(String username) {
        var token = "Bearer " + login();
        try {
            var generalResponse = authProvider.findUserId(username, token);
            var userIdRes = getResponseObject(generalResponse, UserIdRes.class);
            return userIdRes.getId();
        }catch (Exception e){
            log.error(ErrorCode.CORE_SERVICE_CONNECTION_ERROR.getTechnicalMessage());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CONNECTION_ERROR);
        }
    }

    protected String login() {
        var req = new AuthSignInReq();
        req.setUsername(username);
        req.setPassword(password);
        try {
            var generalResponse = authProvider.login(req);
            var tokenRes = getResponseObject(generalResponse,AuthSignInRes.class);
            return tokenRes.getAccessToken();
        } catch (Exception e) {
            log.error(ErrorCode.CORE_SERVICE_CONNECTION_ERROR.getTechnicalMessage());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CONNECTION_ERROR);
        }
    }

    public <T> T getResponseObject(GeneralResponse response, Class<T> tClass){
        if (Boolean.TRUE.equals(response.getIsSuccess())) {
            if (response.getResultData() != null) {
                return mapper.map(response.getResultData(),tClass);
            } else {
                log.error(ErrorCode.CORE_SERVICE_TROUBLE_TO_PARS_DATA.getTechnicalMessage());
                throw new CoreServiceException(ErrorCode.CORE_SERVICE_TROUBLE_TO_PARS_DATA);
            }
        } else {
            log.error(ErrorCode.getTechnicalMessageByCode(response.getRsCode()).getTechnicalMessage());
            throw new CoreServiceException(ErrorCode.getTechnicalMessageByCode(response.getRsCode()));
        }
    }
}

