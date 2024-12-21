    package org.example.endtoendspringmvc_webapplication.Controllers;

    import jakarta.mail.MessagingException;
    import jakarta.servlet.http.HttpServletRequest;
    import lombok.RequiredArgsConstructor;
    import org.example.endtoendspringmvc_webapplication.Config.Password.PasswordRestTokenServiceInt;
    import org.example.endtoendspringmvc_webapplication.Config.Token.VerificationToken;
    import org.example.endtoendspringmvc_webapplication.Config.Token.VerificationTokenRepoInt;
    import org.example.endtoendspringmvc_webapplication.Config.Token.VerificationTokenServiceInt;
    import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
    import org.example.endtoendspringmvc_webapplication.Event.Listener.RegistrationCompleteEventListener;
    import org.example.endtoendspringmvc_webapplication.Event.RegistrationCompleteEvent;
    import org.example.endtoendspringmvc_webapplication.Models.RegistrationRequest;
    import org.example.endtoendspringmvc_webapplication.Services.UserServiceInt;
    import org.example.endtoendspringmvc_webapplication.Utility.UrlUtil;
    import org.springframework.context.ApplicationEventPublisher;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.io.UnsupportedEncodingException;
    import java.net.http.HttpRequest;
    import java.util.Optional;
    import java.util.UUID;

    @Controller
    @RequiredArgsConstructor
    @RequestMapping("/registration")
    public class RegistrationController {

        private final UserServiceInt userService;
        private final ApplicationEventPublisher publisher;
        private final VerificationTokenServiceInt verificationTokenService;
        private final PasswordRestTokenServiceInt passwordRestTokenService;
        private final RegistrationCompleteEventListener eventListener;

        @GetMapping("registration-form")
        public String showRegistrationForm(Model model)
        {
            model.addAttribute("user",new RegistrationRequest());
            return "registration";
        }

        @PostMapping("/register")
        public String registerUser(@ModelAttribute("userEntity") RegistrationRequest registration, HttpServletRequest request)
        {
            var user=userService.registerUser(registration);
            //publish the verification email event here
            publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getAppUrl(request)));

            return "redirect:/registration/registration-form?success";

        }

        @GetMapping("verifyEmail")
        public String verifyEmail(@RequestParam("token") String token)
        {
            Optional<VerificationToken>theToken  =verificationTokenService.findByToken(token);
            if(theToken.isEmpty())
            {
                return "redirect:/error?invalid";
            }
            VerificationToken tokenEntity=theToken.get();
            if(tokenEntity.getUser().getIsEnabled())
            {
                return "redirect:/login?verified";
            }

            String verificationResult=verificationTokenService.validateToken(token);

            return switch (verificationResult.toLowerCase())
            {
                case "valid" -> "redirect:/login?verified";
                case "expired" -> "redirect:/error?expired";
                default -> "redirect:/error?invalid";
            };
        }


        @GetMapping("forgot-password-request")
        public String forgotPassword()
        {
            return "forgot-password-form";
        }

        @PostMapping("forgot-password")
        public String resetPasswordRequest(HttpServletRequest request,Model model)
        {
            String email=request.getParameter("email");
            var user=userService.findUserByEmail(email);

            if(user==null)
            {
                return "redirect:/registration/forgot-password-request?not_found";
            }
                String passwordResetToken= UUID.randomUUID().toString();
                passwordRestTokenService.createPasswordResetTokenForUser(user,passwordResetToken);

                String url=UrlUtil.getAppUrl(request)+"/registration/password-reset-form?token="+passwordResetToken;
            try {
                eventListener.sendPasswordRestVerificationEmail(url);
            } catch (MessagingException |UnsupportedEncodingException  e) {
                model.addAttribute("error",e.getMessage());
            }
            return "redirect:/registration/forgot-password-request?success";

        }

        @GetMapping("password-reset-form")
        public String passwordResetForm(@RequestParam("token") String token,Model model)
        {
            model.addAttribute("token",token);
            return "password-reset-form";
        }

        @PostMapping("reset-password")
        public String resetPassword(HttpServletRequest request)
        {
            String token=request.getParameter("token");
            String newPassword=request.getParameter("password");

            String tokenVerificationResult=passwordRestTokenService.validatePasswordResetToken(token);

            if(!tokenVerificationResult.equalsIgnoreCase("valid"))
            {
                return "redirect:/error?invalid";
            }

            Optional<UserEntity> user=passwordRestTokenService.findUserByPasswordResetToken(token);

            if(user.isPresent())
            {
                passwordRestTokenService.resetPassword(user.get(),newPassword);
                return "redirect:/login?reset_success";
            }
            return "redirect:/error?no_found";

        }

    }
