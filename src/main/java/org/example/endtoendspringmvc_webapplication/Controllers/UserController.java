package org.example.endtoendspringmvc_webapplication.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.endtoendspringmvc_webapplication.Entities.UserEntity;
import org.example.endtoendspringmvc_webapplication.Services.UserServiceInt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInt userService;


    @GetMapping
    public String getUser(Model model)
    {
        model.addAttribute("users",userService.getAllUsers());
        return "users";
    }
    @GetMapping("edit/{id}")
    public String showUserUpdateForm(@PathVariable("id") Long id,Model model)
    {
        Optional<UserEntity> user=userService.findUserById(id);
        model.addAttribute("user",user.get());
        return "update-user";
    }
    @PostMapping("update/{id}")
    public String updateUser(@PathVariable("id") Long id, UserEntity user)//RedirectAttributes how to use
    {
        userService.updateUser(id,user.getFirstName(),user.getLastName(),user.getEmail());
        return "redirect:/users?success_update";
    }


    @GetMapping ("delete_user")
    public String deleteUserByEmail(@RequestParam("email") String userEmail)
    {
        userService.deleteUser(userEmail);
        return "redirect:/users?success_deleted";
    }
}
