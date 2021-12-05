package com.upb.qresent;

import com.upb.qresent.course.Course;
import com.upb.qresent.course.CourseDto;
import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.course.CourseService;
import com.upb.qresent.user.User;
import com.upb.qresent.user.UserRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    private CourseService courseService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        courseService = new CourseService(courseRepository, userRepository);
    }

    @Test
    public void getCourseByID_allcases() {
        Course course = new Course("Mate1", new ObjectId(), (short) 1, "nimic", "nimic", "nimic", new HashSet<String>());
        ObjectId id = new ObjectId();
        course.setId(id);
        courseRepository.save(course);
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        Course actualCourse = courseService.getCourseByID(id);

        assertEquals(course.getId(), actualCourse.getId());
    }

    @Test
    public void enrolStudents_usernull() {
        ObjectId id = new ObjectId();
        User user = null;

        boolean check = courseService.enrollStudents(user, id.toString());

        assertFalse(check);
    }

    @Test
    public void enrolStudents_coursIdnull() {
        User user = null;
        boolean check = courseService.enrollStudents(user, null);

        assertFalse(check);
    }

    @Test
    public void enrolStudents_coursenotnull() {
        Course course = new Course("Mate1", new ObjectId(), (short) 1, "nimic", "nimic", "nimic", new HashSet<String>());
        ObjectId id = new ObjectId();
        course.setId(id);
        courseRepository.save(course);
        User user = new User("Alex", "Student", "", "", new HashSet<>());
        user.insertCourseIntoCourses(id);
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        boolean check = courseService.enrollStudents(user, id.toString());

        assertTrue(check);
    }

    @Test
    public void enrolStudents_coursenotnull2() {
        Course course = new Course("Mate1", new ObjectId(), (short) 1, "nimic", "nimic", "nimic", new HashSet<String>());
        ObjectId id = new ObjectId();
        course.setId(id);
        courseRepository.save(course);
        User user = new User("Alex", "Student", "", "", new HashSet<>());
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        boolean check = courseService.enrollStudents(user, id.toString());

        assertTrue(check);
    }

    @Test
    public void enrolStudents_coursenull() {
        Course course = new Course("Mate1", new ObjectId(), (short) 1, "nimic", "nimic", "nimic", new HashSet<String>());
        ObjectId id = new ObjectId();
        course.setId(id);
        courseRepository.save(course);
        User user = new User("Alex", "Student", "", "", new HashSet<>());
        user.insertCourseIntoCourses(id);
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        boolean check = courseService.enrollStudents(user, "nothing");

        assertTrue(check);
    }

    @Test
    public void getCoursesList_usernull() {
        Course course = new Course("Mate1", new ObjectId(), (short) 1, "nimic", "nimic", "nimic", new HashSet<String>());
        ObjectId id = new ObjectId();
        course.setId(id);
        courseRepository.save(course);
        User user = new User("Alex", "Student", "", "", null);
        assertNull(courseService.getCoursesList(null));
    }

    @Test
    public void getCoursesList_usernotnull() {
        Course course = new Course("Mate1", new ObjectId(), (short) 1, "nimic", "nimic", "nimic", new HashSet<String>());
        ObjectId id = new ObjectId();
        course.setId(id);
        courseRepository.save(course);
        User user = new User("Alex", "Student", "", "", new HashSet<>());
        user.insertCourseIntoCourses(id);
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        courseService.enrollStudents(user, id.toString());
        List<CourseDto> list = courseService.getCoursesList(user);
        assertEquals(user.getCourses().stream().toList(), list);
    }
}


