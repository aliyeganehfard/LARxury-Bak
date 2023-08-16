package ir.larxury.auth.service.service;

import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.repository.projection.UserIdProjection;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

    UserIdProjection findUserIdByUsername(String username);
}
