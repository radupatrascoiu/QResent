package com.upb.qresent.student;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "students")
public class Student {
    @Id
    private ObjectId id;
    private String name;
    private String ldapId;
    private String classroom;
    private String googleId;
    private Set<ObjectId> courses;

    public Student(String name, String ldapId, String classroom, String googleId, Set<ObjectId> courses) {
        this.name = name;
        this.ldapId = ldapId;
        this.classroom = classroom;
        this.googleId = googleId;
        this.courses = courses;
    }


    public boolean insertCourseIntoCourses(ObjectId courseId) {
        return courses.add(courseId);
    }

    public boolean deleteCourseIntoCourses(ObjectId courseId) {
        return courses.remove(courseId);
    }
}
