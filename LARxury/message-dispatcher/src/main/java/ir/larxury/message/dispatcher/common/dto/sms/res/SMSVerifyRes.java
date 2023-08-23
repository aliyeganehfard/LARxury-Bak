package ir.larxury.message.dispatcher.common.dto.sms.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class SMSVerifyRes implements Serializable {

    private Integer status;
}
