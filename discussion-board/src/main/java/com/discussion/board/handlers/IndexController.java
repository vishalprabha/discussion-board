package com.discussion.board.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    //Reference code : https://www.baeldung.com/spring-mvc-thymeleaf-data
    // Route to index page
    @GetMapping("/")
    public String displayIndex() {
        return "index";
    }
}
