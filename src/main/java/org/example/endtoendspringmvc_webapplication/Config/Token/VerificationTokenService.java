package org.example.endtoendspringmvc_webapplication.Config.Token;

import lombok.RequiredArgsConstructor;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.example.endtoendspringmvc_webapplication.Repo.UserRepoInt;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VerificationTokenService implements VerificationTokenServiceInt{

    private final VerificationTokenRepoInt verificationTokenRepo;
    private final UserRepoInt userRepo;

    @Override
    public String validateToken(String token) {

        Optional<VerificationToken> _token=findByToken(token);
        if(_token.isEmpty())
        {
            return "invalid";
        }
        UserEntity user=_token.get().getUser();
        Calendar calendar=Calendar.getInstance();
        if((_token.get().getExpirationTime().getTime()-calendar.getTime().getTime())<=0)
        {
            return "expired";
        }
        user.setIsEnabled(true);
        userRepo.save(user);
        return "Valid";
    }

    @Override
    public void saveVerificationTokenForUser(UserEntity user, String token) {
        var verificationToken=new VerificationToken(token,user);
        verificationTokenRepo.save(verificationToken);

    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepo.findByToken(token);
    }

    @Override
    public void deleteUserToken(String email) {
        verificationTokenRepo.deleteByUserEmail(email);
    }
}
