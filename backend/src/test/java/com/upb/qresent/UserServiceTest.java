package com.upb.qresent;

import com.upb.qresent.course.CourseRepository;
import com.upb.qresent.qrCode.QRCodeService;
import com.upb.qresent.user.UserRepository;
import com.upb.qresent.user.UserService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void getRequestUser() {
        //HttpServletRequest request = new HttpServletRequest();
        //assertNotNull(userService.getRequestUser(request));
    }
}
