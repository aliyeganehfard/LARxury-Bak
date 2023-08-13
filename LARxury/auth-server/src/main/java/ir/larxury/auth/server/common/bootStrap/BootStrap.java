package ir.larxury.auth.server.common.bootStrap;

import ir.larxury.auth.server.database.model.Role;
import ir.larxury.auth.server.database.model.User;
import ir.larxury.auth.server.database.repository.RoleRepository;
import ir.larxury.auth.server.database.repository.UserRepository;
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
                Role.builder().name("ROLE_ADMIN").build(),
                Role.builder().name("ROLE_SUPPORT").build(),
                Role.builder().name("ROLE_SHOP_ADMIN").build(),
                Role.builder().name("ROLE_USER").build()
        );
        roleRepository.saveAll(roles);
        User admin = new User();
        admin.setUsername("admin");
        String adminPass = passwordEncoder.encode("admin");
        admin.setPassword(adminPass);
        admin.setPhoneNumber("09166761607");
        admin.getRoles().add(roles.get(0));
        userRepository.save(admin);
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
