package ir.larxury.core.service.common.dto.messageDispatcher;

import lombok.Data;

import java.io.Serializable;

@Data
public class DispatcherInstantDeliveryReq implements Serializable {

    private String subject;
    private String message;
    private String receiver;
}
