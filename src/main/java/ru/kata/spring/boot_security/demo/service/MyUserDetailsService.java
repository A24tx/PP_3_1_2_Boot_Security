package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MyUserDetailsService implements UserService {

    private UserRepository ur;
    private RoleRepository rr;
    private BCryptPasswordEncoder bcrypt;

    public MyUserDetailsService(UserRepository ur, RoleRepository rr, BCryptPasswordEncoder bcrypt) {
        this.ur = ur;
        this.bcrypt = bcrypt;
        this.rr = rr;
    }

    @Override
    @Transactional
    public void saveUser(User u) {
        u.setPassword(bcrypt.encode(u.getPassword()));
        ur.save(u);
    }

    @Override
    @Transactional
    public void updateUser(User u) {
        Optional<User> fromDB = ur.findById(u.getId());
        if (fromDB.isPresent()) {
            User userFromDB = fromDB.get();
            userFromDB.setUsername(u.getUsername());
            userFromDB.setPassword(bcrypt.encode(u.getPassword()));
        }
    }

    @Override
    public List<User> getUsers() {
        return ur.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return ur.findById(id).orElse(new User());
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        if (ur.findById(id).isPresent()) {
            ur.deleteById(id);
        }
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
