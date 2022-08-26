package com.discussion.board.datastore;
// Handles post data CRUD operation with JPA
import com.discussion.board.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
// The proxy pattern is a technique that allows one object — the proxy — to control access to another object — the subject or service.
// Proxy pattern
//  PostRepository directly access the MySQL DB using JPArepository which act as the proxies
// Reference code : https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
// Reference code : https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
public interface PostRepository extends JpaRepository<Post, Long> {
    // Update operation for liking a post
    @Modifying
    @Transactional
    @Query ("UPDATE Post a SET a.useful = :bool WHERE a.id = :id")
    void setUsefulForPost(@Param("bool") Boolean bool, @Param("id") Long id);
    // Delete posts in threads
    @Transactional
    void deletePostById(Long id);
    // Code to obtain status
    Long countPostsByUser_Id(Long id);
    Long countPostsByUser_IdAndUseful(Long user_id, boolean useful);
    Long countPostsByThread_Id(Long thread_id);

    // Operations used in post controllers
    List<Post> findPostByUser_IdOrderByCreatedDateDesc(Long id);
    List<Post> findPostByUser_IdAndUsefulOrderByCreatedDateDesc(Long user_id, boolean useful);
    List<Post> findPostByThread_Id(Long thread_id);
}
