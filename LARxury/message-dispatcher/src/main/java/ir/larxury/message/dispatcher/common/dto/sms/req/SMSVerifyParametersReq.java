package ir.larxury.message.dispatcher.common.dto.sms.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class SMSVerifyParametersReq implements Serializable {

    private String name;
    private String value;
}
