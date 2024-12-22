package org.example.endtoendspringmvc_webapplication.Config.Events.Listeners;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.endtoendspringmvc_webapplication.Services.VerificationTokenServiceInt;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.example.endtoendspringmvc_webapplication.Config.Events.RegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenServiceInt tokenService;
    private final JavaMailSender mailSender;
    private  UserEntity user;


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user=event.getUser();//get user

        //generate token
            String verificationToken= UUID.randomUUID().toString();

        // save token
            tokenService.saveVerificationTokenForUser(user,verificationToken);

        //build the verification url
            String url=event.getConfirmationUrl()+"/registration/verifyEmail?token="+verificationToken;

        //sen mail to user
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("Error sending verification email: " + e.getMessage());
        }
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject="Email verification";
        String senderName="End To End Project Team";
        String mailContent="<p>Hi, "+user.getFirstName()+"</p>"+
                "<p>Thank you for registration with us,"+""+
                "please, follow the link bellow to complete your registration.</p>"+
                "<a href=\""+url+"\">Verify your email to activate your account</a>"+
                "<p>Thank you<br>End To End Project Team";
        emailMessage(subject,senderName,mailContent,mailSender,user);
    }
    public void sendPasswordRestVerificationEmail(String url,UserEntity user) throws MessagingException, UnsupportedEncodingException {
        String subject="Password Rest Request verification";
        String senderName="End To End Project Team";
        String mailContent = "<p>Hi, " + user.getFirstName() + "</p>" +
                "<p><b>You recently requested to reset your password.</b></p>" +
                "<p>Please, follow the link below to complete the process:</p>" +
                "<a href=\"" + url + "\">Reset your password</a>" +
                "<p>Thank you,<br>End-To-End Project Team</p>";
        emailMessage(subject,senderName,mailContent,mailSender,user);
    }

    private static void emailMessage(String subject, String senderName, String mailContent,
                                     JavaMailSender mailSender, UserEntity user) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message=mailSender.createMimeMessage();
        var messageHelper=new MimeMessageHelper(message);
        messageHelper.setFrom("muhammad4ammar23@gmail.com",senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent,true);
        mailSender.send(message);


    }
}























