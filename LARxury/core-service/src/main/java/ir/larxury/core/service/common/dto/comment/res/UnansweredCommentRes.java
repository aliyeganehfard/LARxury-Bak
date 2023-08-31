package ir.larxury.core.service.common.dto.comment.res;

import ir.larxury.core.service.common.dto.BaseIdDto;
import ir.larxury.core.service.database.model.Product;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UnansweredCommentRes extends BaseIdDto implements Serializable {

    private Date createDate;

    private String text;

    private String commenterUserId;

    private Product product;
}
