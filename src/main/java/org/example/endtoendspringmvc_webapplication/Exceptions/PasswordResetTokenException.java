package org.example.endtoendspringmvc_webapplication.Exceptions;

public class PasswordResetTokenException extends RuntimeException {

    public PasswordResetTokenException(String message) {
        super(message);
    }
}
