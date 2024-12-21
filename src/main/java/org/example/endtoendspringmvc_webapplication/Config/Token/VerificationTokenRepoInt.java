package org.example.endtoendspringmvc_webapplication.Config.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepoInt extends JpaRepository<VerificationToken,Long> {


    Optional<VerificationToken> findByToken(String token);


    void deleteByUserEmail(String email);

}
