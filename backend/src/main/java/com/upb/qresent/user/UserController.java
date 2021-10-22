package com.upb.qresent.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('student')")
    public String index(HttpServletRequest httpServletRequest) {
        return "Bravooo! Te-ai dus in pula cu satelitul";
    }

    @GetMapping("/users")
    public String getAllUsers(HttpServletRequest httpServletRequest) {
        return "Mars ma";
    }
}
