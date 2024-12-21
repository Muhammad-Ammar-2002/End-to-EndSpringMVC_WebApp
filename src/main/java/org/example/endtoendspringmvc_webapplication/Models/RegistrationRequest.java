package org.example.endtoendspringmvc_webapplication.Models;

import lombok.*;
import org.example.endtoendspringmvc_webapplication.Entities.UserRoleEntity;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<UserRoleEntity> roles;

}
