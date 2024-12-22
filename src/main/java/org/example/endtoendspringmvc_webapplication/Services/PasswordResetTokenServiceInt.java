package org.example.endtoendspringmvc_webapplication.Services;

import jakarta.transaction.Transactional;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetTokenServiceInt {
    String validatePasswordResetToken(String token);

    void createPasswordResetTokenForUser(UserEntity user, String passwordResetToken);

    Optional<UserEntity> findUserByPasswordResetToken(String token);

    void resetPassword(UserEntity user, String newPassword);

    @Transactional
    @Modifying
    @Query("DELETE FROM PasswordResetToken p WHERE p.user.id =:id")
    void deleteUserToken(@Param("id") Long id);
}
