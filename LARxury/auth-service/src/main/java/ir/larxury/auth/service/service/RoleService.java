package ir.larxury.auth.service.service;

import ir.larxury.auth.service.database.model.Role;
import ir.larxury.auth.service.database.model.enums.RoleName;

public interface RoleService {

    Role findByName(RoleName roleName);
}
