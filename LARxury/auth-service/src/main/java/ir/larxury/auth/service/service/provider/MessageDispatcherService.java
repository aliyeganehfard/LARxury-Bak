package ir.larxury.auth.service.service.provider;

import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.service.AuthService;
import ir.larxury.auth.service.service.provider.request.MessageDispatcherHttpService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageDispatcherService {

    @Autowired
    private MessageDispatcherHttpService messageDispatcherHttpService;

    @Lazy
    @Autowired
    private AuthService authService;

    public void sendVerify(String code, String phoneNumber) {
        var token = "Bearer " + login();
        var responseEntity =
                messageDispatcherHttpService.sendVerify(code, phoneNumber, token);
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
