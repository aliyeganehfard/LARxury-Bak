package ir.larxury.message.dispatcher.common.dto.email.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InstantDeliveryReq implements Serializable {

    @NotEmpty
    private String subject;

    @NotEmpty
    private String message;

    @NotEmpty
    private String receiver;
}
