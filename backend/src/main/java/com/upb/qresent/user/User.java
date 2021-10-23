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
@AllArgsConstructor
@Document(collection = "students")
public class User {
    @Id
    private ObjectId id;
    private String name;
    private String ldapId;
    private String classroom;
    private String googleId;
    private Set<ObjectId> courses;

    public boolean insertCourseIntoCourses(ObjectId courseId) {
        return courses.add(courseId);
    }

    public boolean deleteCourseIntoCourses(ObjectId courseId) {
        return courses.remove(courseId);
    }
}
