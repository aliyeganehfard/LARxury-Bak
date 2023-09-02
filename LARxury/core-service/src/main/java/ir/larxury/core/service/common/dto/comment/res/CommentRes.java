package ir.larxury.core.service.common.dto.comment.res;

import ir.larxury.core.service.common.dto.BaseIdDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentRes extends BaseIdDto implements Serializable {

    private Date createDate;

    private String text;

    private String commenterUserId;

    private String commenterUsername;

    private BaseIdDto product;
}
