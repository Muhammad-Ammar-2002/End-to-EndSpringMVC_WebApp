package org.example.endtoendspringmvc_webapplication.Services;

import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.example.endtoendspringmvc_webapplication.Models.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserServiceInt {

     List<UserEntity> getAllUsers();
     UserEntity registerUser(RegistrationRequest user);
     UserEntity findUserByEmail(String email);
    void deleteUser(Long id);

    Optional<UserEntity> findUserById(Long id);

    void updateUser(Long id, String firstName, String lastName, String email);
}
