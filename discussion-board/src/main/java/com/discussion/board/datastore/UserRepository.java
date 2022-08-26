package com.discussion.board.datastore;
// Handles Users CRUD operations with JPA
import com.discussion.board.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
// Proxy pattern
//  UserRepository directly access the MySQL DB using JPArepository which act as the proxies
// Reference code : https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
public interface UserRepository extends JpaRepository<User, Long> {

    // CRUD operation used in profile and thread controller
    User getUserByUsername(String username);
    User getUserById(long Id);
    List<User> findAll();
}
