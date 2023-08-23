package ir.larxury.message.dispatcher.common.dto.sms.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class SMSVerifyReq implements Serializable {

    private String mobile;
    private String templateId;
    private SMSVerifyParametersReq[] parameters;

}
