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
    private Set<String> schedule;

    public Course(String name, ObjectId professorId, String infos, String requirements, String bonuses, Set<String> schedule) {
        this.name = name;
        this.professorId = professorId;
        this.infos = infos;
        this.requirements = requirements;
        this.bonuses = bonuses;
        this.schedule = schedule;
    }



    public boolean insertIntervalIntoSchedule(String interval) {
        return schedule.add(interval);
    }

    public boolean deleteIntervalFromSchedule(String interval) {
        return schedule.remove(interval);
    }
}
