package ir.larxury.core.service.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseReqDto implements Serializable {

    @NotEmpty(message = "شناسه نمیتواند خالی باشد")
    @Min(value = 0 ,message = "شناسه باید بزرگ تر از 0 باشد")
    private Long id;
}
