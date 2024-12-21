package org.example.endtoendspringmvc_webapplication.Config.Token;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;

import java.util.Calendar;
import java.util.Date;



public class TokenExpirationTime {

    private static final int EXPIRATION_TIME = 10;

    public static Date getExpirationTime()
        {
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(new Date().getTime());
            calendar.add(Calendar.MINUTE,EXPIRATION_TIME);
            return new Date(calendar.getTime().getTime());
        }


}
