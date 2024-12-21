package org.example.endtoendspringmvc_webapplication.Event;

import lombok.Getter;
import lombok.Setter;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private UserEntity user;
    private String confirmationUrl;
    public RegistrationCompleteEvent(UserEntity user, String confirmationUrl) {
        super(user);
        this.user=user;
        this.confirmationUrl=confirmationUrl;
    }
}
