package com.upb.qresent.statistics;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

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
    private Date courseData;
    Object calculatedValues;
}
