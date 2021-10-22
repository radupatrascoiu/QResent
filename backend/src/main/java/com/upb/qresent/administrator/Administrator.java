package com.upb.qresent.administrator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "administrators")
public class Administrator {
    @Id
    private ObjectId id;
    private String name;
    private String ldapId;
    private String googleId;

    public Administrator(String name, String ldapId, String googleId) {
        this.name = name;
        this.ldapId = ldapId;
        this.googleId = googleId;
    }
}
