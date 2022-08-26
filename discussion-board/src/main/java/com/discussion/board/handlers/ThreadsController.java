package com.discussion.board.handlers;

import com.discussion.board.models.Thread;
import com.discussion.board.datastore.PostRepository;
import com.discussion.board.datastore.ThreadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
// Controller in the MVC
// Mediator
// State pattern is used to seamlessly shift between controllers. From profile -> CreatePost -> List posts -> CreateThread ->
// ListThread, there is a seamless transfer between controllers.
@Controller
public class ThreadsController {
    // Singleton
    private final ThreadRepository threadRepository;
    private final PostRepository postRepository;
    // Autowired singletons are used to inject a bean of the same type into each controller
    // Reference code : https://www.baeldung.com/spring-autowire
    @Autowired
    public ThreadsController(ThreadRepository threadRepository, PostRepository postRepository) {
        this.threadRepository = threadRepository;
        this.postRepository = postRepository;
    }

    // Reference code : https://www.baeldung.com/spring-thymeleaf-request-parameters
    // Get all threads
    @GetMapping("threads")
    public String displayAllThreads(Model model) {
        List<Thread> threads = threadRepository.findAll(new Sort(Sort.Direction.DESC, "createdDate"));
        String header = setHeader("all");
        model.addAttribute("threads", threads);
        model.addAttribute("header", header);
        model.addAttribute("postRepository", postRepository);
        return "threads";
    }
    // Get thread by category
    @GetMapping("threads/{category}")
    public String displayThreadsByCategory(@PathVariable String category, Model model) {
        List<Thread> threads = threadRepository.findThreadsByCategoryOrderByCreatedDateDesc(category);
        String header = setHeader(category);
        model.addAttribute("threads", threads);
        model.addAttribute("header", header);
        model.addAttribute("postRepository", postRepository);
        return "threads";
    }
    // Get thread by user ID
    @GetMapping("threads/user/{id}")
    public String displayThreadsByUser(@PathVariable String id, Model model) {
        List<Thread> threads = threadRepository.findThreadsByUser_IdOrderByCreatedDateDesc(Long.valueOf(id));
        String header = setHeader("user");
        model.addAttribute("threads", threads);
        model.addAttribute("header", header);
        model.addAttribute("postRepository", postRepository);
        return "threads";
    }
    // Switch for setting header
    private String setHeader(String category) {
        switch (category) {
            case "career":
                return "Career";
            case "events":
                return "Events";
            case "general":
                return "General";
            case "courses":
                return "courses";
            case "entertainment":
                return "Entertainment";
            case "all":
                return "All threads";
            default:
                return "User's threads";
        }
    }
}