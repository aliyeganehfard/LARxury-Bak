package ir.larxury.core.service.provider;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.provider.AbstractProviderService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.common.dto.messageDispatcher.DispatcherInstantDeliveryReq;
import ir.larxury.core.service.provider.request.MessageDispatcherHttpProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageDispatcherProvider extends AbstractProviderService {

    @Autowired
    private MessageDispatcherHttpProvider httpService;

    @Autowired
    private AuthServiceProvider authServiceProvider;

    public void instantDelivery(String subject, String message, String receiverEmail) {
        var req = new DispatcherInstantDeliveryReq();
        req.setSubject(subject);
        req.setMessage(message);
        req.setReceiver(receiverEmail);

        var responseEntity = httpService.emailInstantDelivery(req, login());
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            super.validateAndGetResponse(generalResponse);
        } else {
            log.error("problem connection to dispatcher service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CONNECTION_ERROR);
        }
    }

    @Override
    public String login() {
        return "Bearer " + authServiceProvider.getToken();
    }
}
