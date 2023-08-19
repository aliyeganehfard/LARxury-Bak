package ir.larxury.auth.service.common.bootStrap;

import ir.larxury.auth.service.database.model.Role;
import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.model.enums.RoleName;
import ir.larxury.auth.service.database.repository.RoleRepository;
import ir.larxury.auth.service.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BootStrap {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public BootStrap(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void init(){

        List<Role> roles = Arrays.asList(
                Role.builder().name(RoleName.ROLE_ADMIN).build(),
                Role.builder().name(RoleName.ROLE_MANAGER).build(),
                Role.builder().name(RoleName.ROLE_SHOP_ADMIN).build(),
                Role.builder().name(RoleName.ROLE_USER).build()
        );
        roleRepository.saveAll(roles);
        User admin = new User();
        admin.setUsername("admin");
        String adminPass = passwordEncoder.encode("admin");
        admin.setPassword(adminPass);
        admin.setPhoneNumber("09166761607");
        admin.getRoles().add(roles.get(0));
        userRepository.save(admin);


        User manager = new User();
        manager.setUsername("manager");
        String managerPass = passwordEncoder.encode("manager");
        manager.setPassword(managerPass);
        manager.setPhoneNumber("09166761607");
        manager.getRoles().add(roles.get(1));
        userRepository.save(manager);

        User shopAdmin = new User();
        shopAdmin.setUsername("shop");
        String shopAdminPass = passwordEncoder.encode("shop");
        shopAdmin.setPassword(shopAdminPass);
        shopAdmin.setPhoneNumber("09166761607");
        shopAdmin.getRoles().add(roles.get(2));
        userRepository.save(shopAdmin);

        User user = new User();
        user.setUsername("user");
        String userPass = passwordEncoder.encode("user");
        user.setPassword(userPass);
        user.setPhoneNumber("09166761607");
        user.getRoles().add(roles.get(3));
        userRepository.save(user);
//
//        Role adminRole = new Role();
//        adminRole.setName("ROLE_ADMIN");
//        adminRole.setUser(admin);
//        roleRepository.save(adminRole);
//
//        User user = new User();
//        user.setUsername("user");
//        String userPass = passwordEncoder.encode("user");
//        user.setPassword(userPass);
//
//        userRepository.save(user);
//        Role userRole = new Role();
//        userRole.setName("ROLE_USER");
//        userRole.setUser(user);
//        roleRepository.save(userRole);

    }
}
