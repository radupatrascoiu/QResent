package com.upb.qresent.user;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;
    private String name;
    private String role;
    private String ldapId;
    private String classroom;
    private Set<ObjectId> courses;

    public User(String name, String role, String ldapId, String classroom, Set<ObjectId> courses) {
        this.name = name;
        this.role = role;
        this.ldapId = ldapId;
        this.classroom = classroom;
        this.courses = courses;
    }

    public User(String name, String role, String ldapId, Set<ObjectId> courses) {
        this.name = name;
        this.role = role;
        this.ldapId = ldapId;
        this.courses = courses;
    }

    public boolean insertCourseIntoCourses(ObjectId courseId) {
        return courses.add(courseId);
    }

    public boolean deleteCourseIntoCourses(ObjectId courseId) {
        return courses.remove(courseId);
    }
}
