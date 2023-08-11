package ir.larxury.auth.server.database.repository;

import ir.larxury.auth.server.database.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {

    OTP findFirstByUserPhoneNumberOrderByCreateDateDesc(String phoneNumber);
}
