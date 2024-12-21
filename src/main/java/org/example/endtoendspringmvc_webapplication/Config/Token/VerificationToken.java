package org.example.endtoendspringmvc_webapplication.Config.Token;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public VerificationToken(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expirationTime=TokenExpirationTime.getExpirationTime();
    }

}
