package ir.larxury.core.service.service.provider;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.provider.AbstractProviderService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.common.dto.messageDispatcher.DispatcherInstantDeliveryReq;
import ir.larxury.core.service.service.provider.request.MessageDispatcherHttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageDispatcherService extends AbstractProviderService {

    @Autowired
    private MessageDispatcherHttpService httpService;

    @Autowired
    private AuthProvider authProvider;

    public void instantDelivery(String subject, String message, String receiverEmail) {
        var req = new DispatcherInstantDeliveryReq();
        req.setSubject(subject);
        req.setMessage(message);
        req.setReceiver(receiverEmail);

        var responseEntity = httpService.instantDelivery(req, login());
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            var generalResponse = responseEntity.getBody();
            assert generalResponse != null;
            super.validateResponse(generalResponse);
        } else {
            log.error("problem connection to dispatcher service with http status code {}", responseEntity.getStatusCode());
            throw new CoreServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String login() {
        return "Bearer " + authProvider.getToken();
    }
}
