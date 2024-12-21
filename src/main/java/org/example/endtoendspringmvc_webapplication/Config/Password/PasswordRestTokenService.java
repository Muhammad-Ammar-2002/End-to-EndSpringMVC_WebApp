    package org.example.endtoendspringmvc_webapplication.Config.Password;

    import lombok.RequiredArgsConstructor;
    import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
    import org.example.endtoendspringmvc_webapplication.Repo.UserRepoInt;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.Calendar;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    public class PasswordRestTokenService implements PasswordRestTokenServiceInt{

        private final PasswordRestTokenRepo passwordRestTokenRepo;
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

            PasswordResetToken resetToken=new PasswordResetToken(passwordResetToken,user);

             passwordRestTokenRepo.save(resetToken);

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
    }
