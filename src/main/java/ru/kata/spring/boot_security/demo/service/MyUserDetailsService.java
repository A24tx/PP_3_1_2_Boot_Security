package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository ur;
    private RoleRepository rr;
    private BCryptPasswordEncoder bcrypt;

    public MyUserDetailsService(UserRepository ur, RoleRepository rr, BCryptPasswordEncoder bcrypt) {
        this.ur = ur;
        this.bcrypt = bcrypt;
        this.rr = rr;
    }


    public boolean saveUser(User u) {
        User fromDB = ur.findByUsername(u.getUsername());

        if (!(fromDB == null)) {
            return false;
        }
        u.setPassword(bcrypt.encode(u.getPassword()));
        ur.save(u);
        return true;
    }


    public List<User> getUsers() {
        return ur.findAll();
    }

    public User getUserById(Long id) {
        return ur.findById(id).orElse(new User());
    }

    public void removeUser(Long id) {
        if (ur.findById(id).isPresent()) {
            ur.deleteById(id);
        }
    }

    public void addInitialRoles() {
        User user = new User("user", "user");
        User admin = new User("admin", "admin");
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

        saveUser(user);
        saveUser(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails ud = ur.findByUsername(username);
        if (ud == null) {
            return new User();
        }
        return ud;
    }
}
