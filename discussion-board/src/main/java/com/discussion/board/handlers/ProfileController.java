package com.discussion.board.handlers;

import com.discussion.board.models.Thread;
import com.discussion.board.datastore.PostRepository;
import com.discussion.board.datastore.ThreadRepository;
import com.discussion.board.datastore.UserRepository;
import com.discussion.board.models.User;

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
import java.util.Objects;
// Controller in the MVC
// Mediator
// State pattern is used to seamlessly shift between controllers. From profile -> CreatePost -> List posts -> CreateThread ->
// ListThread, there is a seamless transfer between controllers.
@Controller
public class ProfileController {
    // Singleton
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;
    private final PostRepository postRepository;
    // Autowired singletons are used to inject a bean of the same type into each controller
    // Reference code : https://www.baeldung.com/spring-autowire
    @Autowired
    public ProfileController(UserRepository userRepository, ThreadRepository threadRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.threadRepository = threadRepository;
        this.postRepository = postRepository;
    }
    // Get profile details
    // Reference code : https://springhow.com/thymeleaf-form-handling
    @GetMapping("profile")
    public String displayMyProfile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = userRepository.getUserByUsername(username);
        Long numberOfThreads = threadRepository.countThreadsByUser_Id(user.getId());
        Long numberOfPosts = postRepository.countPostsByUser_Id(user.getId());
        Long numberOfHelped = postRepository.countPostsByUser_IdAndUseful(user.getId(), true);
        model.addAttribute("user", user);
        model.addAttribute("numberOfThreads", numberOfThreads);
        model.addAttribute("numberOfPosts", numberOfPosts);
        model.addAttribute("numberOfHelped", numberOfHelped);
        return "profile";
    }
    // handle who created thread
    @GetMapping("createThread")
    public String createThread(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = userRepository.getUserByUsername(username);
        model.addAttribute("user", user);
        return "createThread";
    }
    // Get user status
    @GetMapping("profile/{id}")
    public String displayProfileById(@PathVariable Long id, Model model) {
        User user = userRepository.getUserById(id);
        Long numberOfThreads = threadRepository.countThreadsByUser_Id(id);
        Long numberOfPosts = postRepository.countPostsByUser_Id(id);
        Long numberOfHelped = postRepository.countPostsByUser_IdAndUseful(id, true);
        model.addAttribute("user", user);
        model.addAttribute("numberOfThreads", numberOfThreads);
        model.addAttribute("numberOfPosts", numberOfPosts);
        model.addAttribute("numberOfHelped", numberOfHelped);
        return "profile";
    }

    // Reference code : https://www.baeldung.com/spring-thymeleaf-request-parameters
    @PostMapping("profile")
    public View addTask(@RequestParam("category") String category, @RequestParam("title") String title,
                        @RequestParam("content") String content, @RequestParam(name="code", required=false) String code,
                        @RequestParam("id_user") String id_user, HttpServletRequest request) {
        Thread thread = new Thread();
        thread.setCategory(category);
        // I know that it can be blank field, but I did it on purpose to find out about Optionals:
        if (Objects.equals(code, ""))
            thread.setCode(null);
        else
            thread.setCode(code);
        thread.setContent(content);
        thread.setTitle(title);
        thread.setCreatedDate(LocalDateTime.now());
        thread.setUser(userRepository.getUserById(Long.parseLong(id_user)));
        threadRepository.save(thread);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/threads");
    }
}