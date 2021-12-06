package com.upb.qresent.user;

import org.bson.types.ObjectId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getRequestUser(HttpServletRequest request) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return userRepository.findByLdapId(principal.getName());
    }

    public int getNumberOfStudentsFromACourse(ObjectId courseId) {
        return userRepository.findByCourses(courseId).size();
    }
}
