package org.example.endtoendspringmvc_webapplication.Config;

import lombok.RequiredArgsConstructor;
import org.example.endtoendspringmvc_webapplication.Repo.UserRepoInt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepoInt userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email).map(MyUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
