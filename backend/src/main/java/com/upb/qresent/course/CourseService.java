package com.upb.qresent.course;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    public final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course getCourseByID(ObjectId courseId) {
        return courseRepository.findById(courseId.toString()).orElse(null);
    }
}
