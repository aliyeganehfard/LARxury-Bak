package ir.larxury.core.service.common.dto.comment.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class PostReplyReq implements Serializable {

    @NotEmpty(message = "جواب نمیتواند خالی باشد")
    private String answer;

    @Min(value = 1, message = "شناسه کامنت باید بزرگتر از صفر باشد")
    @NotNull(message = "شناسه کامنت نمیتواند خالی باشد")
    private Long commentId;
}
