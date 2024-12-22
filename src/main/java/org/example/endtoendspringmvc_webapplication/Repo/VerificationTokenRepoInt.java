package org.example.endtoendspringmvc_webapplication.Repo;

import org.example.endtoendspringmvc_webapplication.Entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepoInt extends JpaRepository<VerificationToken,Long> {


    Optional<VerificationToken> findByToken(String token);

//    @Modifying
//    @Query(value = "delete from verification_token v where v.user_id=(select u.id from user_entity u where u.email=:email)",nativeQuery = true)
//    void deleteByUserEmail(@Param("email") String email);

}
