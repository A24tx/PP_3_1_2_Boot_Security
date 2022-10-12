package ru.kata.spring.boot_security.demo.init;

import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

public class InitialUsers {

    public static void addInitialUsersAndRoles(UserService us, RoleRepository rr) {
        User user = new User("user@user.com", "user");
        User admin = new User("admin@admin.com", "admin");
        Role userRole = new Role(1L, "ROLE_USER");
        Role adminRole = new Role(2L, "ROLE_ADMIN");

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
        admin.setRoles(adminRoles);

        rr.save(userRole);
        rr.save(adminRole);

        us.saveUser(user);
        us.saveUser(admin);


    }

}
