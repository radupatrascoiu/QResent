package com.upb.qresent.presentList;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Document(collection = "presenceLists")
public class PresenceList {
    @Id
    private ObjectId id;
    private ObjectId courseId;
    private ObjectId professorId;
    private ObjectId qrId;
    private Date timestampCreated;
    private Date timestampClosed;
    private Set<ObjectId> students;

    public boolean insertStudentIntoPresenceList(ObjectId studentId) {
        return students.add(studentId);
    }

    public boolean deleteStudentFromPresenceList(ObjectId studentId) {
        return students.remove(studentId);
    }
}
