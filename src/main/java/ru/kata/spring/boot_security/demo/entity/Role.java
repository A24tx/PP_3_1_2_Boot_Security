package ru.kata.spring.boot_security.demo.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roletable")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(role_name, role.role_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role_name);
    }

    @Override
    public String toString() {
        return role_name.substring(5, role_name.length());
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
