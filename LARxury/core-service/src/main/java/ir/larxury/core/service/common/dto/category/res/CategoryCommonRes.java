package ir.larxury.core.service.common.dto.category.res;

import ir.larxury.core.service.common.dto.BaseIdDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryCommonRes extends BaseIdDto implements Serializable {
    private String title;
}
