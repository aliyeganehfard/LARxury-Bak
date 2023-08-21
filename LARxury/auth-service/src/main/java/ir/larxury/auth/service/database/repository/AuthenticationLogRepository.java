package ir.larxury.auth.service.database.repository;

import ir.larxury.auth.service.database.model.AuthenticationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationLogRepository extends JpaRepository<AuthenticationLog,Long> {
}
