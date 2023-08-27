package ir.larxury.auth.service.service;

import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.repository.projection.UserIdProjection;
import ir.larxury.auth.service.database.repository.projection.UserInformationProjection;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);

    UserIdProjection findUserIdByUsername(String username);

    UserInformationProjection findUserInformationById(String id);

    void setShopAdminRole(String id);
}
