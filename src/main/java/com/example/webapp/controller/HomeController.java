package com.example.webapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if (authentication != null) {
            boolean isTeacher = authentication.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_TEACHER"));
            model.addAttribute("isTeacher", isTeacher);
        }
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
