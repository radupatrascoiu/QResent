package com.upb.qresent.presentList;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresenceListRepository extends MongoRepository<PresenceList, String> {
    List<PresenceList> findByCourseId(ObjectId courseId);
}
