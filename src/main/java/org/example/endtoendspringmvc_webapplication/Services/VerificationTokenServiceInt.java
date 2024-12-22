package org.example.endtoendspringmvc_webapplication.Services;

import org.example.endtoendspringmvc_webapplication.Entities.VerificationToken;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;

import java.util.Optional;

public interface VerificationTokenServiceInt {

    String validateToken(String token);
        void saveVerificationTokenForUser(UserEntity user,String token);
    Optional<VerificationToken> findByToken(String token);

    void deleteUserToken(Long id);
}
