package ir.larxury.auth.service.service.provider;

import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.service.AuthService;
import ir.larxury.auth.service.service.provider.request.MessageDispatcherHttpService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageDispatcherService {

    @Autowired
    private MessageDispatcherHttpService messageDispatcherHttpService;

    @Lazy
    @Autowired
    private AuthService authService;

    public void sendEmailVerify(String code, String phoneNumber) {
        var token = "Bearer " + login();
        var responseEntity =
                messageDispatcherHttpService.sendEmailVerify(code, phoneNumber, token);
        checkVerifyResponse(responseEntity);
    }

    public void sendSMSVerify(String code, String phoneNumber) {
        var token = "Bearer " + login().trim();
        var responseEntity =
                messageDispatcherHttpService.sendSMSVerify(code, phoneNumber, token);
        checkVerifyResponse(responseEntity);
    }

    private void checkVerifyResponse(ResponseEntity<GeneralResponse> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            if (!generalResponse.getIsSuccess()) {
                log.error("problem to send verify to dispatcher happened with result code {}", generalResponse.getRsCode());
                throw new AuthException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.error("problem connection to dispatcher service with http status code {}", responseEntity.getStatusCode());
            throw new AuthException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    protected String login() {
        return authService.pureGetToken().getAccessToken();
    }
}
