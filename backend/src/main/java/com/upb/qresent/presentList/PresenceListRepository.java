package com.upb.qresent.presentList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresenceListRepository extends MongoRepository<PresenceList, String> {
}
