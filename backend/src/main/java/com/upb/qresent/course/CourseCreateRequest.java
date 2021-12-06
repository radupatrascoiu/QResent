package com.upb.qresent.course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCreateRequest {
    private String courseName;
    private String professorMail;
}
