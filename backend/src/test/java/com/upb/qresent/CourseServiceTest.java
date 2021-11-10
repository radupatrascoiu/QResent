package com.upb.qresent;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CourseServiceTest {
    @Test
    void test(){
        Course course = new Course("Mate1",new ObjectId(),"nimic","nimic","",new HashSet<String>());
        CourseService courseservicetest = null;
        ObjectId id = new ObjectId();
        course.setId(id);
        assertEquals(courseservicetest.getCourseByID(id).getName(),"Mate1");

    }
}


