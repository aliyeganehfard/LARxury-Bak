package ir.larxury.auth.service.service.provider;

import ir.larxury.auth.service.service.provider.request.MessageDispatcherHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageDispatcherService {

    @Autowired
    private MessageDispatcherHttpService messageDispatcherHttpService;

    public void sendVerify(String code, String phoneNumber) {
        messageDispatcherHttpService.sendVerify(code, phoneNumber);
    }
}
