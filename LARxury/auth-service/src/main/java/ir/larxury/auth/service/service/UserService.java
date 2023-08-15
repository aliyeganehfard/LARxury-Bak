package ir.larxury.auth.service.service;

import ir.larxury.auth.service.database.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);
}
