package org.example.endtoendspringmvc_webapplication.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id",unique = true,nullable = false)
    private UserEntity user;

    public PasswordResetToken(String token, UserEntity user) {
        this.token = token;
        this.expirationTime = TokenExpirationTime.getExpirationTime() ;
        this.user = user;
    }
}
