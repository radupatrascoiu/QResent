package com.upb.qresent.course;

import com.upb.qresent.user.UserRepository;
import com.upb.qresent.utils.ResponseDto;
import org.bson.types.ObjectId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "${frontend.domain}", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api")
public class CourseController {
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Value("${frontend.domain}")
    private String CourseUrl;

    public CourseController(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/courses")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCoursesList(HttpServletRequest request) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        var user = userRepository.findByLdapId(principal.getName());
        if (user != null) {
            List<Course> courseList = new ArrayList<>();
            for (ObjectId str : user.getCourses()) {
                courseList.add(courseRepository.findById(str.toString()).orElse(null));
            }
            return ResponseEntity.ok(courseList);
        }

        return ResponseEntity.badRequest().body(new ResponseDto("Bad request", null));
    }
}
