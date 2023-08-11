package ir.larxury.auth.server.database.repository;

import ir.larxury.auth.server.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
