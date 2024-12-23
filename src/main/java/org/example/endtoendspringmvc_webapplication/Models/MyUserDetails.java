package org.example.endtoendspringmvc_webapplication.Models;


import lombok.Data;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MyUserDetails implements UserDetails {

    private final String userName;
    private final String password;
    private final Boolean isEnabled;
    private final List<GrantedAuthority> authorities;

    public MyUserDetails(UserEntity user) {
        this.userName = user.getEmail();
        this.password=user.getPassword();
        this.isEnabled=user.getIsEnabled();
        this.authorities= Arrays.stream(user.getRoles().toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
