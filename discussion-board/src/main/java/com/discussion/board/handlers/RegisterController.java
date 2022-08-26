package com.discussion.board.handlers;

import com.discussion.board.datastore.UserRepository;
import com.discussion.board.models.User;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;

// The singleton pattern is a mechanism that ensures only one instance of an object exists per application
// Spring restricts a singleton to one object per Spring IoC container. Spring creates all beans as singletons

// Mediator
// Controller in the MVC
// State pattern is used to seamlessly shift between controllers. From profile -> CreatePost -> List posts -> CreateThread ->
// ListThread, there is a seamless transfer between controllers.
@Controller
public class RegisterController {
    // Singleton
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // Reference code : https://www.baeldung.com/spring-autowire
    // Autowired singletons are used to inject a bean of the same type into each controller
    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Reference code : https://www.baeldung.com/spring-thymeleaf-request-parameters
    @GetMapping("register")
    public String displayRegister(Model model) {
        return "register";
    }
    // Register user and encrypt store the pwd
    @PostMapping("register")
    public View registerUser(@RequestParam("username") String username, @RequestParam("password") String password,
                             @RequestParam("introduction") String introduction, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        User user = new User();
        if (userRepository.getUserByUsername(username) == null) {
            user.setUsername(username);
            // I know that it can be blank field, but I did it on purpose to find out about Optionals:
            if (Objects.equals(introduction, ""))
                user.setIntroduction(null);
            else
                user.setIntroduction(introduction);
            user.setPassword(password);
            user.setPassword(passwordEncoder.encode(password));
            user.setCreatedDate(LocalDateTime.now());
            userRepository.save(user);
            return new RedirectView(contextPath + "/login");
        } else
            return new RedirectView(contextPath + "/register");
    }
}