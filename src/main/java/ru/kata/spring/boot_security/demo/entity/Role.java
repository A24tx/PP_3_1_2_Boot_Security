package ru.kata.spring.boot_security.demo.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roletable")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    private String role_name;

    public Role() {

    }

    public Role(Long id, String roleName) {
        this.id = id;
        this.role_name = roleName;
    }


    @Override
    public String getAuthority() {
        return role_name;
    }
}
