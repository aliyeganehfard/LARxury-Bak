package ir.larxury.core.service.common.dto.comment.req;

import ir.larxury.core.service.common.dto.BaseIdDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentReq implements Serializable {

    @NotEmpty(message = "کامنت نمیتواند خالی باشد")
    private String text;

    @NotNull(message = "شناسه محصول نمیتواند خالی باشد")
    private BaseIdDto product;

}
