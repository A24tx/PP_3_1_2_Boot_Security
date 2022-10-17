package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
public class UserController {

    private UserServiceImpl myUserService;

    public UserController(UserServiceImpl myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping("/user")
    public String showUserPage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentuser", myUserService.getUserById(user.getId()));

        return "user";
    }

}
