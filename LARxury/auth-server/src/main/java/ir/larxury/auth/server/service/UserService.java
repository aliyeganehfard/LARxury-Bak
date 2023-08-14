package ir.larxury.auth.server.service;

import ir.larxury.auth.server.database.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);
}
