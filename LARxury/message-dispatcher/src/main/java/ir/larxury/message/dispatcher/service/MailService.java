package ir.larxury.message.dispatcher.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("mailService")
@PropertySource(value = "classpath:mail-service.properties")
public class MailService implements Notifier, OTPSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${email.service.mail.sender}")
    private String senderMail;

    @Override
    public void InstantDelivery(String subject, String message, List<String> receiver) {
        var emailMessage = new SimpleMailMessage();
        emailMessage.setFrom(senderMail);
        emailMessage.setTo(receiver.toArray(new String[0]));
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        mailSender.send(emailMessage);
    }

    @Override
    public void send(String code, String receiver) {
        var emailMessage = new SimpleMailMessage();
        emailMessage.setFrom(senderMail);
        emailMessage.setTo(receiver);
        emailMessage.setText(code);
        mailSender.send(emailMessage);
    }
}
