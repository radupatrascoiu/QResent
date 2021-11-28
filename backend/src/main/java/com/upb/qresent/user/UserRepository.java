package com.upb.qresent.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByLdapId(String ldapId);
    User findById(ObjectId id);
    List<User> findByCourses(ObjectId id);
}
