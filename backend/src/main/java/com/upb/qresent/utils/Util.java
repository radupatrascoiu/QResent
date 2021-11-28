package com.upb.qresent.utils;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.presentList.PresenceListRepository;
import com.upb.qresent.qrCode.QRCodeRepository;
import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service
public class Util {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PresenceListRepository presenceListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QRCodeRepository qrCodeRepository;

    public void insertFakeDataInDB() {
        var initialized = courseRepository.findByName("UBD") != null;

        if (initialized) {
            return;
        }

        // Clear the db
        courseRepository.deleteAll();
        presenceListRepository.deleteAll();
        qrCodeRepository.deleteAll();

        // Insert 2 courses
        Course course1 = courseRepository.save(new Course("UBD", null, (short) 3, "Cancer", "Sa ridici mana", "Nu exista", Set.of("Luni: 10-12", "Vineri: 18-20")));
        Course course2 = courseRepository.save(new Course("PP", null, (short) 6, "Greu rau", "Sa rupi", "Sanki", Set.of("Luni: 12-14", "Joi: 18-20")));

        // Create and set professor for the first course
        User professor1 = userRepository.findByLdapId("boicea.alexandru@stud.com");
        if (professor1 == null) {
            professor1 = userRepository.save(new User("Boicea Alexandru", "professor", "boicea.alexandru@stud.com", Set.of(course1.getId())));
        }
        course1.setProfessorId(professor1.getId());
        courseRepository.save(course1);

        // Create and set professor for the second course
        User professor2 = userRepository.findByLdapId("mihnea.muraru@stud.com");
        if (professor2 == null) {
            professor2 = userRepository.save(new User("Mihnea Muraru", "admin", "mihnea.muraru@stud.com", Set.of(course2.getId())));
        }
        course2.setProfessorId(professor2.getId());
        courseRepository.save(course2);

        // Enroll student to the first course
        User student = userRepository.findByLdapId("patrionpatrick@gmail.com");
        if (student != null) {
            student.setCourses(new HashSet<>());
            student.insertCourseIntoCourses(course1.getId());
            userRepository.save(student);
        }
    }

    public User getUserFromRequest(HttpServletRequest httpServletRequest) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) httpServletRequest.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        if (principal == null) return null;
        return userRepository.findByLdapId(principal.getName());
    }
}
