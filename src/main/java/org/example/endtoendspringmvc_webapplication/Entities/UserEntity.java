package org.example.endtoendspringmvc_webapplication.Entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private Boolean isEnabled=false;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL )
    @JoinTable(name = "user_roles"
            ,joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    ,inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Collection<UserRoleEntity> roles;

    public UserEntity(String firstName, String lastName, String email, String password, Collection<UserRoleEntity> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
