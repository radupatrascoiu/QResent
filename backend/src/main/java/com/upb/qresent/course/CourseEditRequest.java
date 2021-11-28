package com.upb.qresent.course;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class CourseEditRequest {
    private String courseId;
    private String name;
    private ObjectId professorId;
    private String infos;
    private String requirements;
    private String bonuses;
    private short credits;
    private String schedule;
}
