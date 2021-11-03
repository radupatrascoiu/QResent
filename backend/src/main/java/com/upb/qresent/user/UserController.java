package com.upb.qresent.user;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('student')")
    public String index(HttpServletRequest httpServletRequest) {
        return "Secured page for STUDENT - This came from backend";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('professor')")
    public String getAllUsers(HttpServletRequest httpServletRequest) {
        return "Professor role";
    }

    @GetMapping("/public")
    public String getPublic(HttpServletRequest httpServletRequest) {
        return "Public Page";
    }
}
