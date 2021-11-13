package com.upb.qresent.course;

import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import com.upb.qresent.user.UserService;
import com.upb.qresent.utils.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "${frontend.domain}", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/courses/")
public class CourseController {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CourseService courseService;

    @Value("${frontend.domain}")
    private String CourseUrl;

    public CourseController(CourseRepository courseRepository, UserRepository userRepository,
                            UserService userService, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.courseService = courseService;
    }

    @PutMapping("/enroll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> studentEnroll(@RequestBody CourseResponse courseBody,
                                           HttpServletRequest request) {
        var user = userService.getRequestUser(request);
        if (courseService.enrollStudents(user, courseBody.getCourseId())) {
            return ResponseEntity.ok(new ResponseDto("Success", true));
        }
        return ResponseEntity.badRequest().body(new ResponseDto("Bad request", false));
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCoursesList(HttpServletRequest request) {
        var user = userService.getRequestUser(request);
        var courses = courseService.getCoursesList(user);
        if (courses != null) {
            return ResponseEntity.ok(courses);
        }
        return ResponseEntity.badRequest().body(new ResponseDto("Bad request", List.of()));
    }
}
