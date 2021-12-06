package com.upb.qresent.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upb.qresent.presentList.PresenceList;
import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import com.upb.qresent.utils.ResponseDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Map<String, Object> getCourseProjection(Course course) {
        Map<String, Object> courseProjection = new HashMap<>();
        courseProjection.put("id", course.getId().toString());
        courseProjection.put("name", course.getName());
        courseProjection.put("credits", course.getCredits());
        courseProjection.put("infos", course.getInfos());
        courseProjection.put("requirements", course.getRequirements());
        courseProjection.put("bonuses", course.getBonuses());
        courseProjection.put("schedule", course.getSchedule());

        // get professor details
        User professor = userRepository.findById(course.getProfessorId().toString()).orElse(null);
        if(professor != null) { professor.setRole(""); courseProjection.put("professor", professor); }

        return courseProjection;
    }
}
