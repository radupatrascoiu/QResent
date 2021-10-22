package com.upb.qresent.qrCode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "qrCodes")
public class QRCode {
    @Id
    private ObjectId id;
    private ObjectId courseId;
    private String link;
    private Date timestampCreated;
    private Date timestampExpires;

    public QRCode(ObjectId courseId, String link, Date timestampCreated, Date timestampExpires) {
        this.courseId = courseId;
        this.link = link;
        this.timestampCreated = timestampCreated;
        this.timestampExpires = timestampExpires;
    }
}
