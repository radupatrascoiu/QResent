package com.upb.qresent.security;

import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@Component
public class UserFilter implements Filter {
    private final UserRepository userRepository;

    public UserFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        if (principal != null && userRepository.findByLdapId(principal.getName()) == null) {
            KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
            AccessToken accessToken = session.getToken();
            User user = new User();

            var emailID = accessToken.getEmail();
            var lastname = accessToken.getFamilyName();
            var firstname = accessToken.getGivenName();

            user.setRole(token.getAuthorities().toString());
            user.setLdapId(emailID);
            user.setName(firstname + " " + lastname);
            user.setCourses(Set.of());
            user.setClassroom("");
            userRepository.save(user);
        }
        filterChain.doFilter(request, response);
    }
}
