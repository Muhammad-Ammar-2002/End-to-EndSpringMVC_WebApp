package org.example.endtoendspringmvc_webapplication.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.example.endtoendspringmvc_webapplication.Entities.UserRoleEntity;
import org.example.endtoendspringmvc_webapplication.Models.RegistrationRequest;
import org.example.endtoendspringmvc_webapplication.Repo.UserRepoInt;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInt{

    private final UserRepoInt userRepoInt;
    private final    PasswordEncoder passEncoder;
    private final VerificationTokenServiceInt verificationTokenService;
    private final PasswordResetTokenServiceInt passwordRestTokenService;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepoInt.findAll();
    }

    @Override
    public UserEntity registerUser(RegistrationRequest user) {
        var userEntity = new UserEntity(user.getFirstName(),user.getLastName(),user.getEmail(),
                passEncoder.encode(user.getPassword()),
                Arrays.asList(new UserRoleEntity("USER_ROLE")));
        return userRepoInt.save(userEntity);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepoInt.findByEmail(email)
                          .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> user=userRepoInt.findById(id);
        user.ifPresent(uzer->verificationTokenService.deleteUserToken(uzer.getId()));
        user.ifPresent(uzer->  passwordRestTokenService.deleteUserToken(uzer.getId()));

        userRepoInt.deleteById(id);
    }

    @Override
    public Optional<UserEntity> findUserById(Long id) {

        return userRepoInt.findById(id);
    }

    @Transactional
    @Override
    public void updateUser(Long id, String firstName, String lastName, String email) {

        userRepoInt.updateUser(firstName,lastName,email,id);
    }
}
