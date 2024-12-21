package org.example.endtoendspringmvc_webapplication.Config.Password;

import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;

import java.util.Optional;

public interface PasswordRestTokenServiceInt {
    String validatePasswordResetToken(String token);

    void createPasswordResetTokenForUser(UserEntity user, String passwordResetToken);

    Optional<UserEntity> findUserByPasswordResetToken(String token);

    void resetPassword(UserEntity user, String newPassword);
}
