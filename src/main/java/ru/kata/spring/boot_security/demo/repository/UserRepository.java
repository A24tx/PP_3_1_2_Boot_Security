package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * > Зачем findByUsername() когда есть loadUserByUsername()?
     * <p>
     * Не понял к чему этот вопрос. Разве это не методы разных слоев
     * приложения?
     */
    @EntityGraph(value = "User.roles")
    User findByUsername(String username);

}
