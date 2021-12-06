package com.upb.qresent.course;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "courses")
public class Course {
    @Id
    private ObjectId id;
    private String name;
    private ObjectId professorId;
    private String infos;
    private String requirements;
    private String bonuses;
    private short credits;
    private String schedule;

    public Course(String name, ObjectId professorId, short credits, String infos, String requirements, String bonuses, String schedule) {
        this.name = name;
        this.professorId = professorId;
        this.infos = infos;
        this.requirements = requirements;
        this.bonuses = bonuses;
        this.schedule = schedule;
        this.credits = credits;
    }

    public Course() {
    }
}
