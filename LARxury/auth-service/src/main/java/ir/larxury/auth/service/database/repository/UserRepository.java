package ir.larxury.auth.service.database.repository;

import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.repository.projection.UserIdProjection;
import ir.larxury.auth.service.database.repository.projection.UserInformationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    @Query("SELECT NEW ir.larxury.auth.service.database.repository.projection.UserIdProjection(user.id) FROM User user WHERE user.username = :username")
    List<UserIdProjection> findUserIdByUsername(@Param(value = "username") String username);

    @Query("SELECT NEW ir.larxury.auth.service.database.repository.projection.UserInformationProjection(user.firstName, user.lastName, user.email ,user.phoneNumber) " +
            "FROM User user " +
            "WHERE user.id = :userId")
    UserInformationProjection getUserInformationById(@Param(value = "userId") UUID userId);
}
