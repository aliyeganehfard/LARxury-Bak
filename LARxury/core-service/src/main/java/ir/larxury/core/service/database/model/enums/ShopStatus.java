package ir.larxury.core.service.database.model.enums;

import lombok.Getter;

@Getter
public enum ShopStatus {
    AWAITING_CONFIRMATION("در انتظار تایید"),
    REJECTION("تایید نشد"),
    CONFIRM("تایید شده"),
    NEW("جدید"),
    ACTIVE("فعال"),
    BLOCKED("مسدود شده");

    private final String description;

    ShopStatus(String description) {
        this.description = description;
    }
}
