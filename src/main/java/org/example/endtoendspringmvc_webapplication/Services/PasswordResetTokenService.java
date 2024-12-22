    package org.example.endtoendspringmvc_webapplication.Services;

    import lombok.Data;
    import lombok.RequiredArgsConstructor;
    import org.example.endtoendspringmvc_webapplication.Entities.PasswordResetToken;
    import org.example.endtoendspringmvc_webapplication.Entities.TokenExpirationTime;
    import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
    import org.example.endtoendspringmvc_webapplication.Exceptions.PasswordResetTokenException;
    import org.example.endtoendspringmvc_webapplication.Repo.PasswordResetTokenRepo;
    import org.example.endtoendspringmvc_webapplication.Repo.UserRepoInt;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.Calendar;
    import java.util.Date;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    public class PasswordResetTokenService implements PasswordResetTokenServiceInt {

        private final PasswordResetTokenRepo passwordRestTokenRepo;
        private final UserRepoInt userRepoInt;
        private final PasswordEncoder passwordEncoder;
        @Override
        public String validatePasswordResetToken(String token) {

            Optional<PasswordResetToken> passwordRestToken=passwordRestTokenRepo.findByToken(token);
            if(passwordRestToken.isEmpty())
            {
                return "invalid";
            }
            Calendar calendar=Calendar.getInstance();
            if(passwordRestToken.get().getExpirationTime().getTime()-calendar.getTime().getTime()<=0)
            {
                return "expired";
            }
            return "valid";
        }

        @Override
        public void createPasswordResetTokenForUser(UserEntity user, String passwordResetToken) {
            Optional<PasswordResetToken> existingTokenOpt = passwordRestTokenRepo.findById(user.getId());

            if (existingTokenOpt.isPresent()) {
                PasswordResetToken existingToken = existingTokenOpt.get();
                Date expirationTime = existingToken.getExpirationTime();

                // Check if the token is expired
                if (expirationTime.before(new Date())) {
                    // Update the existing token with a new token and expiration time
                    existingToken.setToken(passwordResetToken);
                    existingToken.setExpirationTime(TokenExpirationTime.getExpirationTime());
                    passwordRestTokenRepo.save(existingToken);
                    return; // Exit after updating the existing token
                } else {
                    // Throw an exception if a valid token is still in progress
                    throw new PasswordResetTokenException(
                            "A password reset request is already in progress. " +
                                    "Please check your email for the reset link or try again later."
                    );
                }
            } else {
                // Save a new token if no existing valid token is found
                passwordRestTokenRepo.save(new PasswordResetToken(passwordResetToken, user));
            }
        }
        @Override
        public Optional<UserEntity> findUserByPasswordResetToken(String token) {
            return Optional.ofNullable(passwordRestTokenRepo.findByToken(token).get().getUser());
        }

        @Override
        public void resetPassword(UserEntity user, String newPassword) {

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepoInt.save(user);

        }

        @Override
        public void deleteUserToken(Long id) {
            passwordRestTokenRepo.deleteById(id);
        }
    }
