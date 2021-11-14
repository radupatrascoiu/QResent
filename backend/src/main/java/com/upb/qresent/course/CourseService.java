package com.upb.qresent.course;

import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import com.upb.qresent.utils.ResponseDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    public final CourseRepository courseRepository;
    public final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Course getCourseByID(ObjectId courseId) {
        return courseRepository.findById(courseId.toString()).orElse(null);
    }

    public boolean enrollStudents(User user, String courseId) {
        if (user != null && courseId != null) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course != null) {
                if (user.getCourses().contains(course.getId())) {
                    user.getCourses().remove(course.getId());
                } else {
                    user.getCourses().add(course.getId());
                }
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public List<CourseDto> getCoursesList(User user) {
        if (user != null) {
            List<Course> courseList = courseRepository.findAll();
            List<CourseDto> courses = new ArrayList<>();
            for (Course course : courseList) {
                User professor = userRepository.findById(course.getProfessorId().toString())
                        .orElse(null);
                if (user.getCourses().contains(course.getId())) {
                    courses.add(new CourseDto(course, true, professor));
                } else {
                    courses.add(new CourseDto(course, false, professor));
                }
            }
            return courses;
        }
        return null;
    }
}
