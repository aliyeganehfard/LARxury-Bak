package ir.larxury.message.dispatcher.service;


import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.message.dispatcher.common.aop.exception.DispatcherException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Qualifier("mailService")
@PropertySource(value = "classpath:mail-service.properties")
public class MailService implements Notifier {

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${email.service.mail.sender}")
    private String senderMail;

    @Override
    public void InstantDelivery(String subject, String message, String receiver) {
        try {
            var emailMessage = new SimpleMailMessage();
            emailMessage.setFrom(senderMail);
            emailMessage.setTo(receiver);
            emailMessage.setSubject(subject);
            emailMessage.setText(message);
            mailSender.send(emailMessage);
            log.info("sending instant delivery with email with subject {} was successful", subject);
        } catch (Exception ex) {
            log.error(ErrorCode.DISPATCHER_TROUBLE_IN_INSTANT_DELIVERY + " with subject {}", subject);
            throw new DispatcherException(ErrorCode.DISPATCHER_TROUBLE_IN_INSTANT_DELIVERY, ex);
        }
    }

    @Override
    public void sendOtp(String code, String receiver) {
        try {
            var emailMessage = new SimpleMailMessage();
            emailMessage.setFrom(senderMail);
            emailMessage.setTo(receiver);
            emailMessage.setSubject("LARxury");
            emailMessage.setText("verification code is : " + code);
            mailSender.send(emailMessage);
            log.info("sending otp for receiver {} was successful", receiver);
        } catch (Exception ex) {
            log.error(ErrorCode.DISPATCHER_TROUBLE_IN_SEND_OTP + " for receiver {} ", receiver);
            throw new DispatcherException(ErrorCode.DISPATCHER_TROUBLE_IN_SEND_OTP, ex);
        }
    }
}
