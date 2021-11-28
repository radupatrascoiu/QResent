package com.upb.qresent.statistics;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends MongoRepository<Statistics, String> {
    Statistics findByCourseNo(int courseNo);
}
