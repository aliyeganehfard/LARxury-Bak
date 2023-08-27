package ir.larxury.core.service.common.dto.category.res;

import ir.larxury.core.service.common.dto.BaseReqDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryCommonRes extends BaseReqDto implements Serializable {
    private String title;
}
