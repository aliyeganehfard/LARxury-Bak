package ir.larxury.auth.service.database.repository.projection;

import lombok.Data;

import java.util.UUID;

@Data
public class UserIdProjection {
    private UUID id;

    public UserIdProjection(UUID id) {
        this.id = id;
    }
}
