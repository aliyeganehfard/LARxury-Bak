package ir.larxury.core.service.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseReqDto implements Serializable {

    @NotNull(message = "شناسه نمیتواند خالی باشد")
    private Long id;
}
