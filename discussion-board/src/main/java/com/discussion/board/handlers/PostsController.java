package com.discussion.board.handlers;

import com.discussion.board.models.Post;
import com.discussion.board.datastore.PostRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
// PostController in our MVC acts as our mediator between the model and the views
// This limits direct communication between components in the system and control the flow

// Controller in the MVC
// Mediator

// State pattern is used to seamlessly shift between controllers. From profile -> CreatePost -> List posts -> CreateThread ->
// ListThread, there is a seamless transfer between controllers.
@Controller
public class PostsController {
    // Singleton
    private final PostRepository postRepository;
    // Reference code : https://www.baeldung.com/spring-autowire
    // Autowired singletons are used to inject a bean of the same type into each controller
    @Autowired
    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    // Get User posts
    //Reference code : https://springhow.com/thymeleaf-form-handling
    @GetMapping("posts/{id}")
    public String displayPostsByUser(@PathVariable String id, Model model) {
        List<Post> posts = postRepository.findPostByUser_IdOrderByCreatedDateDesc(Long.parseLong(id));
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("posts/useful/{id}")
    public String displayUsefulPostsByUser(@PathVariable String id, Model model) {
        List<Post> posts = postRepository.findPostByUser_IdAndUsefulOrderByCreatedDateDesc(Long.parseLong(id), true);
        model.addAttribute("posts", posts);
        return "posts";
    }
}
