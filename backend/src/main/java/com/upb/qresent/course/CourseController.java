package com.upb.qresent.course;

import org.springframework.stereotype.Controller;

@Controller
public class CourseController {
    private CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
