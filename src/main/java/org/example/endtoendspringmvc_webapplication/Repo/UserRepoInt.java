package org.example.endtoendspringmvc_webapplication.Repo;

import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepoInt extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);

//    @Procedure(procedureName = "deleteUserByEmail")
//    void deleteUser(String userEmail);

    @Modifying
    @Query(value = "update UserEntity u set u.firstName=:firstName," +
            "u.lastName=:lastName,u.email=:email where u.id=:id")
    void updateUser(String firstName, String lastName, String email, Long id);
    @Modifying
    @Query(value = "delete from UserEntity u where u.email=:userEmail")
    void deleteByEmail(@Param("userEmail") String userEmail);
}
