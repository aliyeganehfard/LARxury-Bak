package ir.larxury.core.service.common.dto.category.res;

import ir.larxury.core.service.common.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryCommonRes extends BaseDto implements Serializable {
    private String title;
}
