package ir.larxury.auth.service.database.repository;

import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.repository.projection.UserIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT NEW ir.larxury.auth.service.database.repository.projection.UserIdProjection(user.id) FROM User user WHERE user.username = :username")
    List<UserIdProjection> findUserIdByUsername(@Param(value = "username") String username);
}
