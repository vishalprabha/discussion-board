package com.discussion.board.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    //Reference code : https://www.baeldung.com/spring-mvc-thymeleaf-data
    // Route to login page
    @GetMapping("login")
    public String displayLogin() {
        return "login";
    }
}