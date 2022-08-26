package com.discussion.board.handlers;

import com.discussion.board.models.Post;
import com.discussion.board.models.Thread;
import com.discussion.board.datastore.PostRepository;
import com.discussion.board.datastore.ThreadRepository;
import com.discussion.board.datastore.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
// Controller in the MVC
// Mediator
// State pattern is used to seamlessly shift between controllers. From profile -> CreatePost -> List posts -> CreateThread ->
// ListThread, there is a seamless transfer between controllers.
@Controller
public class ThreadController {
    // Singleton
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;
    private final PostRepository postRepository;
    // Autowired singletons are used to inject a bean of the same type into each controller
    // Reference code : https://www.baeldung.com/spring-autowire
    @Autowired
    public ThreadController(UserRepository userRepository, ThreadRepository threadRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.threadRepository = threadRepository;
        this.postRepository = postRepository;
    }
    // Reference code : https://www.baeldung.com/spring-thymeleaf-request-parameters
    // Get the post
    @GetMapping("thread/{id}")
    public String displayThread(@PathVariable String id,  Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Long idUser = userRepository.getUserByUsername(username).getId();

        Thread thread = threadRepository.findThreadById(Long.valueOf(id));
        List<Post> posts = postRepository.findPostByThread_Id(Long.valueOf(id));

        model.addAttribute("thread", thread);
        model.addAttribute("posts", posts);
        model.addAttribute("idUser", idUser);
        return "thread";
    }
    // Reference code : https://www.baeldung.com/spring-thymeleaf-request-parameters
    // Update a post
    @PostMapping("thread/{id}")
    public View updatePost(@RequestParam String id_thread, @RequestParam String action, @RequestParam String id_post,
                             @RequestParam(required = false) String state, HttpServletRequest request) {
        switch (action) {
            case "useful" :
                postRepository.setUsefulForPost(!Boolean.valueOf(state), Long.valueOf(id_post));
                break;
            case "delete" :
                postRepository.deletePostById(Long.valueOf(id_post));
                break;
        }
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/thread/" + id_thread);
    }

    // Reference code : https://www.baeldung.com/spring-thymeleaf-request-parameters
    // Create a post
    @PostMapping("thread")
    public View addPost(@RequestParam("content") String content,
                          @RequestParam("id_thread") String id_thread, @RequestParam("id_user") String id_user,
                          HttpServletRequest request) {
        Post post = new Post();
        post.setContent(content);

        post.setCode(null);
        post.setCreatedDate(LocalDateTime.now());
        post.setUseful(false);
        post.setThread(threadRepository.findThreadById(Long.valueOf(id_thread)));
        post.setUser(userRepository.getUserById(Long.parseLong(id_user)));

        postRepository.save(post);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/thread/" + id_thread);
    }
}