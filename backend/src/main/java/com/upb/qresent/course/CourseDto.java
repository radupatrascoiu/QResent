package com.upb.qresent.course;

import com.upb.qresent.user.User;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class CourseDto {
    private String courseId;
    private String name;
    private User professor;
    private String infos;
    private String requirements;
    private String bonuses;
    private short credits;
    private Set<String> schedule;
    private boolean isEnrolled;

    public CourseDto(Course course, boolean isEnrolled, User professor) {
        courseId = course.getId().toString();
        name = course.getName();
        this.professor = professor;
        infos = course.getInfos();
        requirements = course.getRequirements();
        bonuses = course.getBonuses();
        schedule = course.getSchedule();
        this.isEnrolled = isEnrolled;
        this.credits = course.getCredits();
    }
}
