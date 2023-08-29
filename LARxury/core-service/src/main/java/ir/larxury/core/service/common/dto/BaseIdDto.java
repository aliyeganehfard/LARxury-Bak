package ir.larxury.core.service.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseIdDto implements Serializable {

    @NotNull(message = "شناسه نمیتواند خالی باشد")
    private Long id;
}
