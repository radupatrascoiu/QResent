package com.upb.qresent.qrCode;

import com.upb.qresent.utils.Constants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "qrCodes")
public class QRCode {
    @Id
    private ObjectId id;
    private ObjectId presenceListId;
    private Date timestampCreated;
    private Date timestampExpires;

    public QRCode(ObjectId presenceListId, Date timestampCreated, Date timestampExpires) {
        this.presenceListId = presenceListId;
        this.timestampCreated = timestampCreated;
        this.timestampExpires = timestampExpires;
    }

    public QRCode(ObjectId presenceListId) {
        this.presenceListId = presenceListId;
        this.timestampCreated = Date.from(Instant.now());
        this.timestampExpires = Date.from(Instant.now().plus(Duration.ofMillis(Constants.qrCodeExpirationTime)));
    }
}
