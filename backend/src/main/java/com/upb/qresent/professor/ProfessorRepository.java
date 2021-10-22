package com.upb.qresent.professor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends MongoRepository<Professor, String> {
}
