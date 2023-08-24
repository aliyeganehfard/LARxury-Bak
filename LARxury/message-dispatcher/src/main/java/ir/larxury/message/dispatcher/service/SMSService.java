package ir.larxury.message.dispatcher.service;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.message.dispatcher.common.aop.exception.DispatcherException;
import ir.larxury.message.dispatcher.common.dto.sms.req.SMSVerifyParametersReq;
import ir.larxury.message.dispatcher.common.dto.sms.req.SMSVerifyReq;
import ir.larxury.message.dispatcher.service.request.SMSServiceHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Qualifier("SMSService")
@PropertySource(value = "classpath:sms-service.properties")
public class SMSService implements Notifier {

    @Autowired
    private SMSServiceHttpRequest serviceHttpRequest;

    @Value("${sms.service.x-api-key}")
    private String apiKey;

    @Value("${sms.service.templateId}")
    private String templateId;

    @Override
    public void InstantDelivery(String subject, String message, String receiver) {
        //todo
    }

    @Override
    public void sendOtp(String code, String receiver) {
        try {
            var req = new SMSVerifyReq();
            req.setMobile(getCorrectPhoneNumber(receiver));
            req.setTemplateId(templateId);

            var reqParam = new SMSVerifyParametersReq();
            reqParam.setValue(code);
            reqParam.setName("code");
            req.setParameters(new SMSVerifyParametersReq[]{reqParam});
            log.info("sending otp for receiver {} was successful", receiver);
            serviceHttpRequest.send(req,apiKey);
        } catch (Exception ex) {
            log.error(ErrorCode.DISPATCHER_TROUBLE_IN_SEND_OTP + " for receiver {} ", receiver);
            throw new DispatcherException(ErrorCode.DISPATCHER_TROUBLE_IN_SEND_OTP, ex);
        }
    }

    private String getCorrectPhoneNumber(String phoneNumber) {
        var correctPhone = phoneNumber;
        if (phoneNumber.startsWith("0")) {
            correctPhone = phoneNumber.substring(1);
        } else if (phoneNumber.startsWith("98")) {
            correctPhone = phoneNumber.substring(2);
        }
        return correctPhone;
    }
}
