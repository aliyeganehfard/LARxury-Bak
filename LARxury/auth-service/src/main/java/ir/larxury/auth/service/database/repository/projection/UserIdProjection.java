package ir.larxury.auth.service.database.repository.projection;

import lombok.Data;

@Data
public class UserIdProjection {
    private Long id;

    public UserIdProjection(Long id) {
        this.id = id;
    }
}
