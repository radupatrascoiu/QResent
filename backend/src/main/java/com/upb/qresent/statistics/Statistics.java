package com.upb.qresent.statistics;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "statistics")
public class Statistics {
    @Id
    private ObjectId id;
    private ObjectId courseId;
    private int courseNo;
    private LocalDateTime courseData;
    List<Integer> calculatedValues;
    // listele de prezenta
    List<ObjectId> presentList;

    public Statistics(ObjectId courseId, int courseNo, LocalDateTime courseData, List<Integer> calculatedValues, List<ObjectId> presentList) {
        this.courseId = courseId;
        this.courseNo = courseNo;
        this.courseData = courseData;
        this.calculatedValues = calculatedValues;
        this.presentList = presentList;
    }
}
