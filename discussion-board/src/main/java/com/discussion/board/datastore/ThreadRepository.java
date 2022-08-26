package com.discussion.board.datastore;
// Handles Thread CRUD operations with JPA
import com.discussion.board.models.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
// Proxy pattern
// ThreadRepository directly access the MySQL DB using JPArepository which act as the proxies
// Reference code : https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
public interface ThreadRepository extends JpaRepository<Thread, Long> {

    // CRUD operation used in thread controller
    Long countThreadsByUser_Id(Long id);
    Thread findThreadById(Long id);
    List<Thread> findThreadsByCategoryOrderByCreatedDateDesc(String category);
    List<Thread> findThreadsByUser_IdOrderByCreatedDateDesc(Long id);
}
