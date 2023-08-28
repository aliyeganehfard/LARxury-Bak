package ir.larxury.core.service.common.dto.shop.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShopRes implements Serializable {
}
