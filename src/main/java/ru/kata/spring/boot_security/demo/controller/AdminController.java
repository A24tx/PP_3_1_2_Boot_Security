package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Arrays;

@Controller
public class AdminController {
    private UserServiceImpl myUserService;

    public AdminController(UserServiceImpl myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", myUserService.getUsers());
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("newuser", new User());
        model.addAttribute("allroles", myUserService.getAllRoles());
        return "admin";
    }

    @RequestMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        myUserService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user")
    public String showAdminPageForUser(@PathVariable(value = "userId") long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", myUserService.getUsers());
        model.addAttribute("currentuser", currentUser);
        User u = myUserService.getUserById(id);

        return "admin";
    }



    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id, Model model) {
        myUserService.removeUser(id);
        return "redirect:/admin";
    }


    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        myUserService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/adminEdit/{id}")
    public String openEditModal(@PathVariable(value = "id") long id, Model model) {
        User viewUser = myUserService.getUserById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", myUserService.getUsers());
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("viewuser", viewUser);
        model.addAttribute("userroles", Arrays.asList(viewUser.getRoles().toArray()));
        return "adminEditWindow";
    }

    @GetMapping("/admin/adminDelete/{id}")
    public String openDelteModal(@PathVariable(value = "id") long id, Model model) {
        User viewUser = myUserService.getUserById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", myUserService.getUsers());
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("viewuser", viewUser);
        model.addAttribute("userroles", Arrays.asList(viewUser.getRoles().toArray()));
        return "adminDeleteWindow";
    }

    @GetMapping("/admin/viewUser/{id}")
    public String showAdminUserView(@PathVariable(value = "id") long id, Model model) {
        User viewUser = myUserService.getUserById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", myUserService.getUsers());
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("viewuser", viewUser);
        model.addAttribute("userroles", Arrays.asList(viewUser.getRoles().toArray()));
        return "adminUserView";
    }





}
