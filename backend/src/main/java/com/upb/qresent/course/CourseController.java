package com.upb.qresent.course;

import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import com.upb.qresent.user.UserService;
import com.upb.qresent.utils.ResponseDto;
import org.bson.types.ObjectId;
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

    @GetMapping("/{courseId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCourse(@PathVariable(value="courseId") ObjectId courseId) {
        var course = courseService.getCourseByID(courseId);
        if (course == null) {
            return ResponseEntity.badRequest().body(new ResponseDto("This course does not exists.", false));
        }
        Object courseProjection = courseService.getCourseProjection(course);
        return ResponseEntity.ok(new ResponseDto("Success", courseProjection));
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

    @PutMapping("/create")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createCourse(@RequestBody CourseCreateRequest createRequest,
                                           HttpServletRequest request) {
        var professor = userRepository.findByLdapId(createRequest.getProfessorMail());
        if (professor != null) {
            if (professor.getRole().contains("professor")) {
                Course course = new Course();
                course.setName(createRequest.getCourseName());
                course.setProfessorId(professor.getId());
                return ResponseEntity.ok(new ResponseDto("Success", courseRepository.save(course)));
            }
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
