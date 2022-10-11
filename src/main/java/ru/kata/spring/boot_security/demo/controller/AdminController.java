package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;

@Controller
public class AdminController {
    private MyUserDetailsService myUserService;

    public AdminController(MyUserDetailsService myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping("/admin")
    public String showUserList(Model model) {
        model.addAttribute("users", myUserService.getUsers());
        return "admin";
    }

    @RequestMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        myUserService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/newUserForm")
    public String showFormForAddingUser(Model model) {
        User u = new User();
        model.addAttribute("user", u);
        return "addUser";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id, Model model) {
        myUserService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/updateUserForm/{id}")
    public String showFormForUpdatingUser(@PathVariable(value = "id") long id, Model model) {
        User user = myUserService.getUserById(id);

        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        myUserService.saveUser(user);
        return "redirect:/admin";
    }

}
