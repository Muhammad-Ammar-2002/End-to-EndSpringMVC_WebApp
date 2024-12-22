package org.example.endtoendspringmvc_webapplication.Repo;

import org.example.endtoendspringmvc_webapplication.Entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByToken(String token);

    boolean findByUserId(Long id);
}
