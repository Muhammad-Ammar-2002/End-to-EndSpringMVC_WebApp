package org.example.endtoendspringmvc_webapplication.Exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(PasswordResetTokenException.class)
    public String handelPasswordResetTokenExistsException(PasswordResetTokenException ex, Model model)
    {
        model.addAttribute("reset_error",ex.getMessage());
        return "forgot-password-form";
    }
}
