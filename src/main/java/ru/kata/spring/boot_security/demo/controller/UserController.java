package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;

@Controller
public class UserController {
    @Autowired
    private MyUserDetailsService myUserService;

    @GetMapping("/user")
    public String showUserList(Model model) {
        model.addAttribute("users", myUserService.getUsers());
        return "user";
    }

}
